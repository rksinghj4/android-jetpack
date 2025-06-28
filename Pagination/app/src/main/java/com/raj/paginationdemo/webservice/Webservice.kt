package com.raj.paginationdemo.webservice

import com.raj.paginationdemo.model.QuotesResponse
import com.raj.paginationdemo.model.RickAndMortyResponse
import com.raj.paginationdemo.model.SearchReposResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {
    //https://quotable.io/quotes??page=1
    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): QuotesResponse

    //https://rickandmortyapi.com/api/character?page=1
    @GET("/character")
    suspend fun fetchRickAndMorty(@Query("page") page: Int): RickAndMortyResponse

    //https://api.github.com/search/repositories?sort=stars&q=android&page=1&per_page=20

    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): SearchReposResponse

}