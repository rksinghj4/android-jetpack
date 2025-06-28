package com.raj.paginationdemo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raj.paginationdemo.model.SearchReposResponse
import com.raj.paginationdemo.webservice.Webservice
import javax.inject.Inject

class SearchReposPagingSource @Inject constructor(
    private val query: String,
    private val webservice: Webservice
) :
    PagingSource<Int, SearchReposResponse.Repo>() {
    private var itemsPerPage: Int = 20

    override fun getRefreshKey(state: PagingState<Int, SearchReposResponse.Repo>): Int? {
        itemsPerPage = state.config.pageSize
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchReposResponse.Repo> {

        return try {
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            val response = webservice.searchRepos(query, currentPage, itemsPerPage)

            LoadResult.Page(
                data = response.repos,
                prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (currentPage*itemsPerPage >= response.totalCount) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}