package com.raj.paginationdemo.di

import com.raj.paginationdemo.common.DefaultDispatcherProvider
import com.raj.paginationdemo.common.DispatcherProvider
import com.raj.paginationdemo.repository.QuotesRepository
import com.raj.paginationdemo.repository.QuotesRepositoryImpl
import com.raj.paginationdemo.webservice.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class QuotesModule {
    @Provides
    fun providesDispatcher(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    fun providesRepository(webservice: Webservice): QuotesRepository =
        QuotesRepositoryImpl(webservice)
}