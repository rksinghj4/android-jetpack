package com.raj.paginationdemo.repository

import androidx.paging.PagingData
import com.raj.paginationdemo.model.SearchReposResponse
import kotlinx.coroutines.flow.Flow

interface SearchReposRepository {
    fun searchRepos(query: String): Flow<PagingData<SearchReposResponse.Repo>>
}