package com.example.tweetsy.repository

import com.example.tweetsy.api.TweetsyAPI
import com.example.tweetsy.models.TweetListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TweetsRepository @Inject constructor(private val tweetsyAPI: TweetsyAPI) {

    val _category = MutableStateFlow<List<String>>(emptyList())
    val category: StateFlow<List<String>>
        get() = _category

    val _tweets = MutableStateFlow<List<TweetListItem>>(emptyList())
    val tweets: StateFlow<List<TweetListItem>>
        get() = _tweets

    suspend fun getCategory() {
        withContext(Dispatchers.IO) {
            val response = tweetsyAPI.getCategories()
            if (response.isSuccessful && response.body() != null) {
                _category.emit(response.body() as List<String>)
            }
        }
    }

    suspend fun getTweets(category: String) {
        val response = tweetsyAPI.getTweets("tweets[?(@.category == \"$category\")]")
        if (response.isSuccessful && response.body() != null) {
            _tweets.emit(response.body() as List<TweetListItem>)
        }
    }
}