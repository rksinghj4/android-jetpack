package com.raj.paginationdemo.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.raj.paginationdemo.BuildConfig
import com.raj.paginationdemo.webservice.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.nerdythings.okhttp.profiler.OkHttpProfilerInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
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
    fun providesKotlinXSerializationConvertor(): Converter.Factory =
        Json { ignoreUnknownKeys = true  }.asConverterFactory("application/json".toMediaType())

    @Provides
    fun providesOkHttpBuilder(): OkHttpClient.Builder {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
            //Use either one OkHttpProfilerInterceptor or HttpLoggingInterceptor
            //builder.addInterceptor(logger)
        }
        return builder
    }

    @Provides
    fun providesOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
        return builder.build()
    }

    @Provides
    fun providesWebService(
        baseUrl: String,
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        converterFactory: Converter.Factory
    ): Webservice {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .build().create(Webservice::class.java)
    }
}