package com.example.tweetsy.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tweetsy.repository.TweetsRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModels @Inject constructor(private val respository: TweetsRepository): ViewModel() {

    val category: StateFlow<List<String>>
        get() = respository.category

    init {
        viewModelScope.launch {
            respository.getCategory()
        }
    }
}