package com.kotlinflow.di

import android.app.Application
import androidx.room.Room
import com.kotlinflow.database.AppDatabase
import com.kotlinflow.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    //Note: Application instance is available in SingletonComponent by default
    @Provides
    @Singleton
    fun appDatabase(application: Application): AppDatabase = Room.databaseBuilder(
        context = application,
        klass = AppDatabase::class.java,
        "learn-kotlin-flow"
    ).fallbackToDestructiveMigration()
        .build()

    //@Singleton ensures that the UserDao instance is created only once
    // during the application's lifecycle.
    @Provides
    @Singleton
    fun getUserDao(appDatabase: AppDatabase): UserDao = appDatabase.getUserDao()
}


