package com.kotlinflow.webservice

import com.kotlinflow.models.ApiUser
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<ApiUser>

    @GET("more-users")
    suspend fun getMoreUsers(): List<ApiUser>

    @GET("error")
    suspend fun getUsersWIthError(): List<ApiUser>
}