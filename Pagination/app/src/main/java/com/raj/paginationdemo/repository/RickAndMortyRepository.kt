package com.raj.paginationdemo.repository

import androidx.paging.PagingData
import com.raj.paginationdemo.model.RickAndMortyResponse
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun fetchRickAndMorty(): Flow<PagingData<RickAndMortyResponse.Result>>
}