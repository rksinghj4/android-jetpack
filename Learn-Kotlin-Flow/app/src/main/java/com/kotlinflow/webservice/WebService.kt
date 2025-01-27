package com.kotlinflow.webservice

import com.kotlinflow.models.User
import retrofit2.http.GET

interface WebService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("more-users")
    suspend fun getMoreUsers(): List<User>

    @GET("error")
    suspend fun getUsersWIthError(): List<User>
}