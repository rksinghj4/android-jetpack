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
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchUsers() {
        viewModelScope.launch(Dispatchers.Main) {
            val userMutableList = mutableListOf<User>()
            //Current line is on main thread
            userRepository.getUsers()
                .flowOn(dispatcher)
                .catch {
                    //Catching on main
                    _uiStateFlow.emit(UiState.Error(it.toString()))
                }
                .flowOn(Dispatchers.Main)
                .flatMapConcat {
                    /**
                     * flatMapConcat: For long running operation like n/w call or db operation we use flatMapConcat
                     * map: For normal on the fly operation like transformation/sorting of data, use map operator
                     */
                    //Running on IO
                    userMutableList.addAll(it)
                    //Note: In flatMapConcat, last statement of lambda must be Flow<T>
                    userRepository.getMoreUsers()
                }
                .flowOn(dispatcher)
                .catch {
                    //Catching on main
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