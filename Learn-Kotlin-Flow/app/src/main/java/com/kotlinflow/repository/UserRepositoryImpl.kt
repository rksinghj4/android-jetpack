package com.kotlinflow.repository

import com.kotlinflow.models.User
import com.kotlinflow.webservice.WebService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val webService: WebService
) : UserRepository {

    override fun getUsers(): Flow<List<User>> {
        return flow {
            emit(webService.getUsers())
        }
    }

    override fun getMoreUsers(): Flow<List<User>> =
        flow { emit(webService.getMoreUsers()) }

    override fun getUsersWithError(): Flow<List<User>> =
        flow { emit(webService.getUsersWIthError()) }
}