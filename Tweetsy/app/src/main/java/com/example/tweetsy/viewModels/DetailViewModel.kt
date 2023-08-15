package com.example.tweetsy.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tweetsy.models.TweetListItem
import com.example.tweetsy.repository.TweetsRepository
import com.example.tweetsy.utils.CATEGORY
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val repository: TweetsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val tweets: StateFlow<List<TweetListItem>>
        get() = repository.tweets

    init {
        viewModelScope.launch {
            /**
             * getting navargument from SavedStateHandle
             */
            repository.getTweets(category = savedStateHandle.get<String>(CATEGORY) ?: "Motivation")
        }
    }
}