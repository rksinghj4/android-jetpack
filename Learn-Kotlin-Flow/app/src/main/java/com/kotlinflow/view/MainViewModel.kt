package com.kotlinflow.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel : ViewModel() {

    private val _showLoadingIndicatorStateFlow = MutableStateFlow(false)
    internal val showLoadingIndicatorStateFlow = _showLoadingIndicatorStateFlow.asStateFlow()

    private val _errorSharedFlow = MutableSharedFlow<Boolean>()
    val errorSharedFlow = _errorSharedFlow.asSharedFlow()

    fun dismissErrorDialog() {
        viewModelScope.launch {
            _errorSharedFlow.emit(false)
        }
    }

}