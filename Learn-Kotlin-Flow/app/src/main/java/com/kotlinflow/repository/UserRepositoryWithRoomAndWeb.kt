package com.kotlinflow.repository

import com.kotlinflow.database.UserDb
import kotlinx.coroutines.flow.Flow

interface UserRepositoryWithRoomAndWeb {
    suspend fun getUsers(): Flow<List<UserDb>>
    fun getMoreUsers(): Flow<List<UserDb>>
    fun getUsersWithError(): Flow<List<UserDb>>
}