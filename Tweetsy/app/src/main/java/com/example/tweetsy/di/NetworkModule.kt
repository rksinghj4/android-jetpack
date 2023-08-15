package com.example.tweetsy.di

import com.example.tweetsy.api.TweetsyAPI
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.jsonbin.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesTweetsyAPI(retrofit: Retrofit) : TweetsyAPI {
        return retrofit.create(TweetsyAPI::class.java)
    }
}