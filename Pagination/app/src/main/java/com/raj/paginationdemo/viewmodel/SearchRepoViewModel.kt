package com.raj.paginationdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.raj.paginationdemo.repository.SearchReposRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchRepoViewModel @Inject constructor(
    private val reposRepository: SearchReposRepository
) : ViewModel() {
    val searchRepoFlow = reposRepository.searchRepos("android").cachedIn(viewModelScope)
}