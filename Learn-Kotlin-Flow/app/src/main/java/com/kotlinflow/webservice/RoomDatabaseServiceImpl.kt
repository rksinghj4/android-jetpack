package com.kotlinflow.webservice

import com.kotlinflow.database.AppDatabase
import com.kotlinflow.database.UserDao
import com.kotlinflow.database.UserDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Developer specific implementation of RoomDatabaseService(i.e developer specific contract/interface)
 */
class RoomDatabaseServiceImpl @Inject constructor(private val userDao: UserDao) :
    RoomDatabaseService {
    override suspend fun getUsers(): Flow<List<UserDb>> {
        return flow { emit(userDao.getUsers()) }
    }

    override suspend fun insertAll(users: List<UserDb>): Flow<Unit> {
        return flow {
            emit(userDao.insertAll(users))
        }
    }
}