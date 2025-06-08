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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
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

    /**
     * Best Practice (for parallel network calls):
     * Use combine() or coroutineScope + async for truly parallel execution.
     *
     * Under the hood:
     * combine() starts two coroutines to collect flow1 and flow2 concurrently.
     * Because of flowOn(Dispatchers.IO), those two collector coroutines run on the IO dispatcher.
     * As either flow emits, combine() recombines the latest values and emits downstream.
     * ------------------------------------------------------------------------------------
     * How is zip different from combine()?
     * Ans. If we replace combine with zip in below code then zip is still sequential
     *
     * Operator	  Starts 2 coroutines?	 Emits when	 Pair the values                          Parallel network calls?
     * zip()	      ❌ No	              Both emit	      ✅ Yes (wait for both new)           ❌ No
     * combine()	✅ Yes	              Any emits	      ✅ Yes (wait for first pair)         ✅ Yes
     * merge()      ✅ Yes	              Any emits	      ❌ No                               ✅ Yes
     */


    private fun fetchUsers() {
        viewModelScope.launch(Dispatchers.Main) {
            //Main thread
            userRepository.getUsersWithError().catch {
                //Handle gracefully if getUsersWithError() fails
                Log.d("$TAG 2", it.toString())
                emitAll(flowOf(emptyList()))
            }.combine(userRepository.getUsers().catch {
                //Handle gracefully if getUsers() fails
                Log.d("$TAG 2", it.toString())
                emitAll(flowOf(emptyList()))
            }) { users, moreUsers ->
                //To pair it up, wait for slower one
                val allUsers = mutableListOf<User>()
                allUsers.addAll(users)
                allUsers.addAll(moreUsers)
                return@combine allUsers
            }
                .flowOn(dispatcher)
                .catch {
                    //Handle here if some exception happens during pairing them up
                    //Main thread
                    Log.d("$TAG Result", it.toString())
                    _errorSharedFlow.emit(true)
                    _uiStateFlow.emit(UiState.Error(it.toString()))
                }.collect {
                    //Note: We need to handle the case where both apis failed and we come here with empty response.
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