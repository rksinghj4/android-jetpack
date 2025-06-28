package com.raj.paginationdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.raj.paginationdemo.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {
    /**
     * If you use .cachedIn(viewModelScope), the flow survives configuration changes and
     * the paging state is preserved. Without caching, the data is reloaded on every rotation.
     */
    internal val rickAndMortyFlow = rickAndMortyRepository.fetchRickAndMorty().cachedIn(viewModelScope)
}