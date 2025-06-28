package com.raj.paginationdemo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raj.paginationdemo.model.RickAndMortyResponse
import com.raj.paginationdemo.webservice.Webservice
import javax.inject.Inject
const val STARTING_PAGE_INDEX = 1
class RickAndMortyPagingSource @Inject constructor(private val webservice: Webservice) :
    PagingSource<Int, RickAndMortyResponse.Result>() {
    override fun getRefreshKey(state: PagingState<Int, RickAndMortyResponse.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RickAndMortyResponse.Result> {

        return try {
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            val response = webservice.fetchRickAndMorty(currentPage)
            LoadResult.Page(
                data = response.results,
                prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (currentPage == response.info.pages) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}