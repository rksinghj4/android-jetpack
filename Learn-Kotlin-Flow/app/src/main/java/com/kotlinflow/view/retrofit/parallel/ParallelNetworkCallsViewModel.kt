package com.kotlinflow.view.retrofit.parallel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinflow.models.UiState
import com.kotlinflow.models.User
import com.kotlinflow.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParallelNetworkCallsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    init {
        fetchUsers()
    }

    private val _uiStateFlow = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    internal val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _errorSharedFlow = MutableSharedFlow<Boolean>()
    internal val errorSharedFlow = _errorSharedFlow.asSharedFlow()

    private fun fetchUsers() {
        viewModelScope.launch(Dispatchers.Main) {
            //Main thread
            userRepository.getUsersWithError().zip(userRepository.getUsers().catch {
                //Handle gracefully if getUsers() fails
                Log.d("$TAG 2", it.toString())
                emitAll(flowOf(emptyList()))
            }) { users, moreUsers ->
                //To pair it up, wait for slower one
                val allUsers = mutableListOf<User>()
                allUsers.addAll(users)
                allUsers.addAll(moreUsers)
                return@zip allUsers
            }
            .flowOn(dispatcher)
            .catch {
                //Handle here if some exception happens during pairing them up
                //Main thread
                Log.d("$TAG Result", it.toString())
                _errorSharedFlow.emit(true)
                _uiStateFlow.emit(UiState.Error(it.toString()))
            }.collect {
                //main thread
                _uiStateFlow.emit(UiState.Success(it))
            }
        }
    }

    fun dismissErrorScreen() {
        viewModelScope.launch {
            _errorSharedFlow.emit(false)//shared flow doesn't remember
        }
    }


    /**
     * 1. Two parallel network calls N1, N2 -: N1.zip(N2).{ single list }
     * 2. Convert api data into database data -: map { database data list }
     * 3. Save the converted data in database -: flatmapConcat{ save in db }
     * 4. Show on UI -: collect{  }
     */

    /**
     * 1. UploadProfileImage(filepath) : Flow<String> i.e. returns Flow<ProfileUrl>
     * 2. UpdateProfile(username, ProfileUrl): Flow<Response>
     * 3. Convert Response into UIData
     *
     * Solution:
     * UploadProfileImage(filepath)
     * .flatMapConcat{profileUrl -> UpdateProfile(username, profileUrl)}
     * .map{}
     * .flowOn(IO)
     * .collect { }
     */

    companion object {
        const val TAG = "PARALLEL_NW_CALLS"
    }
}