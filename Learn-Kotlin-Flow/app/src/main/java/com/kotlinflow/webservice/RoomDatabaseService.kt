package com.kotlinflow.webservice

import com.kotlinflow.database.UserDb
import kotlinx.coroutines.flow.Flow

/**
 * Just Declare the services needed from the RoomDadabase library here.
 */
interface RoomDatabaseService {//It's developer specific contract/interface, what we need from room
    suspend fun getUsers(): Flow<List<UserDb>>

    suspend fun insertAll(users: List<UserDb>): Flow<Unit>
}