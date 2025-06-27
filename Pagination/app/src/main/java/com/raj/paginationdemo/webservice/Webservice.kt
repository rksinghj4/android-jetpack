package com.raj.paginationdemo.webservice

import com.raj.paginationdemo.model.QuotesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {

    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): QuotesResponse
}