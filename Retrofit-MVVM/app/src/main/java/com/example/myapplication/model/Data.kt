package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class ApiUser(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("avatar")
    val avatar: String = ""
)


sealed class NetworkState {
    object Loading : NetworkState()

    data class Success(val listOfUser: List<ApiUser>): NetworkState()

    data class Error(val errorMessage: String): NetworkState()
}