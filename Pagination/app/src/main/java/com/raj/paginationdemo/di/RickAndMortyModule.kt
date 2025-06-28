package com.raj.paginationdemo.di

import com.raj.paginationdemo.repository.RickAndMortyRepository
import com.raj.paginationdemo.repository.RickAndMortyRepositoryImpl
import com.raj.paginationdemo.webservice.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
class RickAndMortyModule {
    @Provides
    fun providesRickAndMortyRepository(webservice: Webservice): RickAndMortyRepository =
        RickAndMortyRepositoryImpl(webservice)
}