package com.kotlinflow.repository

import com.kotlinflow.models.User
import com.kotlinflow.webservice.WebService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Even though we are using @Inject constructor with UserRepositoryImpl still @Binds/@Provides
 * is must to get UserRepositoryImpl object wherever UserRepository is expected
 */
//
//
class UserRepositoryImpl @Inject constructor(
    private val webService: WebService
) : UserRepository {

    override fun getUsers(): Flow<List<User>> {
        //In which scope getUsers() method is called - i.e main. So this line is on the main thread.

        return flow {
            //But inside the flow builder we are on io thread because flowOn(Dispatchers.IO)
            emit(webService.getUsers())
        }
    }

    override fun getMoreUsers(): Flow<List<User>> =
        flow { emit(webService.getMoreUsers()) }

    override fun getUsersWithError(): Flow<List<User>> =
        flow { emit(webService.getUsersWIthError()) }
}