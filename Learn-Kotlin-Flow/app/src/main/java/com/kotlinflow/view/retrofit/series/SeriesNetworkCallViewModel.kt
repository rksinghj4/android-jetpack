package com.kotlinflow.view.retrofit.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinflow.models.UiState
import com.kotlinflow.models.User
import com.kotlinflow.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sample Code for Sequential Network Calls
 *
 * val sequentialFlow = flow {
 *     val userProfile = apiService.fetchUserProfile() // 🚀 First API call
 *     emit(userProfile)
 *
 *     val userPosts = apiService.fetchUserPosts(userProfile.id) // ✅ Second API after first completes
 *     emit(userPosts)
 * }.flowOn(Dispatchers.IO) // Run on IO thread
 *
 * Collect the Flow
 * sequentialFlow.collect { result ->
 *     when (result) {
 *         is UserProfile -> println("Got user: ${result.name}")
 *         is List<*> -> println("Got posts: ${result.size}")
 *     }
 * }
 *
 * Don't use zip() or combine() for Sequential Network Calls.
 * They are used for independent flows. Here, we have dependency between calls.
 */

@HiltViewModel
class SeriesNetworkCallViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    init {
        fetchUsers()
    }

    private val _uiStateFlow = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    internal val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _errorSharedFlow = MutableSharedFlow<Boolean>()
    internal val errorSharedFlow = _errorSharedFlow.asSharedFlow()

    /**
     * Two perform the tasks in series we can use
     * 1. map -> if 2nd task is not a long running operation
     * 2. flatMapConcat -> if 2nd task is long running operation
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchUsers() {
        viewModelScope.launch(Dispatchers.Main) {
            val userMutableList = mutableListOf<User>()
            //Current line is on main thread
            userRepository.getUsers()
                .catch {
                    emitAll(flowOf(emptyList()))
                }
                .flatMapConcat {
                    /**
                     * flatMapConcat: For long running operation like n/w call or db operation we use flatMapConcat
                     * map: For normal on the fly operation like transformation/sorting of data, use map operator
                     */
                    //Running on IO
                    userMutableList.addAll(it)
                    /**
                     * Note: In flatMapConcat, LastStatement of lambda must be Flow<T>
                     * flatMapConcat - returns Flow<LastStatement> i.e. Flow<Flow<T>>
                     * where as map return just Flow<T>
                     */
                    userRepository.getMoreUsers().catch {
                        emitAll(flowOf(emptyList()))
                    }
                }
                .flowOn(dispatcher)
                .catch {
                    //Catching on main
                    _errorSharedFlow.emit(true)
                    _uiStateFlow.emit(UiState.Error(it.toString()))
                }
                .collect { moreUsers ->
                    //Collection on main
                    userMutableList.addAll(moreUsers)
                    _uiStateFlow.emit(UiState.Success(userMutableList))
                }
        }
    }

    fun dismissErrorScreen() {
        viewModelScope.launch {
            _errorSharedFlow.emit(false)
        }
    }
}