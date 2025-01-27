package com.kotlinflow.view.retrofit.single

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinflow.models.User
import com.kotlinflow.models.UiState
import com.kotlinflow.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleNetworkCallViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    init {
        fetchUsers()
    }

    private val _uiStateFlow =
        MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _errorSharedFlow = MutableSharedFlow<Boolean>()
    val errorSharedFlow = _errorSharedFlow.asSharedFlow()

    private fun fetchUsers() {
        viewModelScope.launch {
            userRepository.getUsers()
                .flowOn(dispatcher)
                .catch {
                    _errorSharedFlow.emit(true)
                    _uiStateFlow.value = UiState.Error(it.toString())
                }.collect {
                    _uiStateFlow.value = UiState.Success(it)
                }
        }
    }

    fun dismissErrorScreen() {
        viewModelScope.launch {
            _errorSharedFlow.emit(false)
        }
    }
}