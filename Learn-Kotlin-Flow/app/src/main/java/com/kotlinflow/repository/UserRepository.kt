package com.kotlinflow.repository

import com.kotlinflow.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    fun getMoreUsers(): Flow<List<User>>
    fun getUsersWithError(): Flow<List<User>>
}