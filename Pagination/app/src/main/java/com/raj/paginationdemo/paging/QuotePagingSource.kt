package com.raj.paginationdemo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raj.paginationdemo.model.QuotesResponse
import com.raj.paginationdemo.webservice.Webservice
import javax.inject.Inject

//https://developer.android.com/codelabs/android-paging-basics#4
class QuotePagingSource @Inject constructor(private val webservice: Webservice) :
    PagingSource<Int, QuotesResponse.Result>() {

    /**
     * When loading from a new PagingSource, getRefreshKey() is called to provide the key the
     * new PagingSource should start loading with to make sure the user does not lose their current
     * place in the list after the refresh.
     *
     * Invalidation in the paging library occurs for one of two reasons:
     * 1. You called refresh() on the PagingAdapter.
     * 2. You called invalidate() on the PagingSource.
     */

    override fun getRefreshKey(state: PagingState<Int, QuotesResponse.Result>): Int? {
        //anchorPosition - Most recently accessed index in the list, including placeholders.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuotesResponse.Result> {

        return try {
            val currentPage = params.key ?: 1
            val quotesResponse = webservice.getQuotes(currentPage)
            return LoadResult.Page(
                data = quotesResponse.results,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage == quotesResponse.totalPages) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}