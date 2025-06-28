package com.raj.paginationdemo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.raj.paginationdemo.model.SearchReposResponse
import com.raj.paginationdemo.paging.SearchReposPagingSource
import com.raj.paginationdemo.webservice.Webservice
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchReposRepositoryImpl @Inject constructor(private val webservice: Webservice) :
    SearchReposRepository {
    override fun searchRepos(query: String): Flow<PagingData<SearchReposResponse.Repo>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 60),
            pagingSourceFactory = {
                SearchReposPagingSource(query, webservice)
            }
        ).flow
    }
}