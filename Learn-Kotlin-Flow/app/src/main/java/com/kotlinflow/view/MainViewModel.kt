package com.kotlinflow.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlinflow.models.User
import com.kotlinflow.models.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    internal val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _errorSharedFlow = MutableSharedFlow<Boolean>()
    val errorSharedFlow = _errorSharedFlow.asSharedFlow()

    fun dismissErrorDialog() {
        viewModelScope.launch {
            _errorSharedFlow.emit(false)
        }
    }

}