package com.raj.paginationdemo.repository

import androidx.paging.PagingData
import com.raj.paginationdemo.model.QuotesResponse
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {
    fun getQuotes(): Flow<PagingData<QuotesResponse.Result>>
}