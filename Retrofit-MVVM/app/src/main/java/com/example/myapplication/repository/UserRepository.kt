package com.example.myapplication.repository

import com.example.myapplication.model.ApiUser
import com.example.myapplication.webservice.ApiService

class UserRepository (private val apiService: ApiService) {

    suspend fun getUsers(): List<ApiUser> = apiService.getUsers()

    suspend fun getMoreUsers(): List<ApiUser> = apiService.getMoreUsers()

    suspend fun getUsersWithError(): List<ApiUser> = apiService.getMoreUsers()
}


