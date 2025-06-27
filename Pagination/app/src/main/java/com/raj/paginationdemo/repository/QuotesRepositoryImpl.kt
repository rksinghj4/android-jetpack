package com.raj.paginationdemo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.raj.paginationdemo.model.QuotesResponse
import com.raj.paginationdemo.paging.QuotePagingSource
import com.raj.paginationdemo.webservice.Webservice
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(private val webservice: Webservice) :
    QuotesRepository {
    override fun getQuotes(): Flow<PagingData<QuotesResponse.Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {
                QuotePagingSource(webservice)
            }
        ).flow
    }
}