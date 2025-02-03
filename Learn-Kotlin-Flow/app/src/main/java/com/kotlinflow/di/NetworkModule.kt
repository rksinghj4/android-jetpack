package com.kotlinflow.di

import com.kotlinflow.repository.UserRepository
import com.kotlinflow.repository.UserRepositoryImpl
import com.kotlinflow.repository.UserRepositoryWithRoomAndWeb
import com.kotlinflow.repository.UserRepositoryWithRoomAndWebImpl
import com.kotlinflow.webservice.RoomDatabaseService
import com.kotlinflow.webservice.RoomDatabaseServiceImpl
import com.kotlinflow.webservice.WebService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(ViewModelComponent::class)
@Module
object NetworkModule {
    private const val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"

    @Provides
    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun webServiceImpl(): WebService = getRetrofit().create(WebService::class.java)

    /*@Provides
    fun getUserRepository(webService: WebService): UserRepository = UserRepositoryImpl(webService)
*/
    @Provides
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun getUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    fun getUserRepositoryWithRoomAndWeb(repository: UserRepositoryWithRoomAndWebImpl): UserRepositoryWithRoomAndWeb

    @Binds
    fun getRoomDatabaseService(repository: RoomDatabaseServiceImpl): RoomDatabaseService
}