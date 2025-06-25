package com.kotlinflow.repository

import com.kotlinflow.database.UserDb
import com.kotlinflow.webservice.RoomDatabaseService
import com.kotlinflow.webservice.WebService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Two perform the tasks in series we can use
 * 1. map -> if 2nd task is not a long running operation
 * 2. flatMapConcat -> if 2nd task is long running operation
 */

class UserRepositoryWithRoomAndWebImpl @Inject constructor(
    private val roomService: RoomDatabaseService,
    private val webService: WebService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepositoryWithRoomAndWeb {

    //Not we return data from db(i.e UserDb) here
    override suspend fun getUsers(): Flow<List<UserDb>> {
        return roomService.getUsers().flatMapConcat { userListFromDB ->
            if (userListFromDB.isEmpty()) {
                return@flatMapConcat flow {
                    emit(webService.getUsers())//Long running operation
                }.map { usersFromWeb ->
                    val userList = mutableListOf<UserDb>()
                    //Short running operation
                    usersFromWeb.forEach {
                        userList.add(
                            UserDb(
                                id = it.id,
                                name = it.name,
                                email = it.email,
                                avatar = it.avatar
                            )
                        )
                    }
                    userList
                }.flatMapConcat { usersReadyForDBInsertaion ->
                    //Long running operation
                    roomService.insertAll(usersReadyForDBInsertaion).flatMapConcat {
                        flow { emit(usersReadyForDBInsertaion) }
                    }
                }
            } else {
                return@flatMapConcat flow { emit(userListFromDB) }
            }
        }.flowOn(dispatcher)
    }

    override fun getMoreUsers(): Flow<List<UserDb>> {
        TODO("Not yet implemented")
    }

    override fun getUsersWithError(): Flow<List<UserDb>> {
        TODO("Not yet implemented")
    }
}