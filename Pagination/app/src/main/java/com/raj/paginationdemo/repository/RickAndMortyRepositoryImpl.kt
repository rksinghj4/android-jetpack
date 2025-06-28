package com.raj.paginationdemo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.raj.paginationdemo.model.RickAndMortyResponse
import com.raj.paginationdemo.paging.RickAndMortyPagingSource
import com.raj.paginationdemo.webservice.Webservice
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Pager should live in the Repository, not the ViewModel.
 *
 * Why Pager Belongs in the Repository Layer
 * Repository = Data source abstraction
 * Whether you're fetching from:
 *
 * API (remote)
 *
 * Room DB (local)
 *
 * or both (RemoteMediator)
 *
 * Paging is part of the data layer, so the repository owns the Pager.
 *
 *
 */
@Singleton
class RickAndMortyRepositoryImpl @Inject constructor(private val webservice: Webservice) :
    RickAndMortyRepository {
    override fun fetchRickAndMorty(): Flow<PagingData<RickAndMortyResponse.Result>> {
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 50),
            pagingSourceFactory = {
                RickAndMortyPagingSource(webservice)
            }
        ).flow
    }
}