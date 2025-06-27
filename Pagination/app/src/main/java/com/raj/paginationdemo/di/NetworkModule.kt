package com.raj.paginationdemo.di

import com.raj.paginationdemo.BuildConfig
import com.raj.paginationdemo.webservice.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun providesBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    fun providesGsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun providesWebService(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): Webservice {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build().create(Webservice::class.java)
    }
}