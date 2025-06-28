package com.raj.paginationdemo.di

import com.raj.paginationdemo.repository.SearchReposRepository
import com.raj.paginationdemo.repository.SearchReposRepositoryImpl
import com.raj.paginationdemo.webservice.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class SearchRepoModule {
    @Provides
    fun providesSearchRepoRepository(webservice: Webservice): SearchReposRepository =
        SearchReposRepositoryImpl(webservice)
}