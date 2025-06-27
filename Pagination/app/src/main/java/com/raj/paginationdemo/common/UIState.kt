package com.raj.paginationdemo.common

sealed interface UIState<out T> {
    object Loading : UIState<Nothing>
    data class Success<T>(val data: T) : UIState<T>
    data class Error(val errMsg: String) : UIState<Nothing>
}