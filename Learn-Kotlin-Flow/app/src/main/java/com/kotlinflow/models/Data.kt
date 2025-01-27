package com.kotlinflow.models

import com.google.gson.annotations.SerializedName

data class ApiUser(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("avatar")
    val avatar: String
)