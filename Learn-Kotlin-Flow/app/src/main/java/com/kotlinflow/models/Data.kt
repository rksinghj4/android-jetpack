package com.kotlinflow.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("avatar")
    val avatar: String
)

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>

    data class Error(val message: String) : UiState<Nothing>

    object Loading : UiState<Nothing>
}