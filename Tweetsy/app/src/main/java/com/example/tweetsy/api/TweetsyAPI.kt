package com.example.tweetsy.api

import com.example.tweetsy.models.TweetListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TweetsyAPI {
    /**
     * We can pass dynamic header here using X-JSON-Path and category param.
     */
    @GET("/v3/b/64d737d3b89b1e2299cf76d3?meta=false")
    suspend fun getTweets(@Header("X-JSON-Path") category: String): Response<List<TweetListItem>>

    @GET("/v3/b/64d737d3b89b1e2299cf76d3?meta=false")
    @Headers("X-JSON-Path: tweets..category") // Static header
    suspend fun getCategories(): Response<List<String>>


}
