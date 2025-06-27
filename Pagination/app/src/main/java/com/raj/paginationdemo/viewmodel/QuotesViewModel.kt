package com.raj.paginationdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.raj.paginationdemo.common.DispatcherProvider
import com.raj.paginationdemo.repository.QuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val repository: QuotesRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    /**
     * 1. We don't need to manually launch coroutine when using Paging 3 library.
     * Under the hood paging library uses coroutines for fetching the pages.
     *
     * 2. No need to have suspend function in viewmodel & repository
     * load method of PagingSource is suspend function which can call webservice's suspend function.
     */

    internal val quotesFlow = repository.getQuotes().cachedIn(viewModelScope)

    /* private val _quotesFlow =
         MutableStateFlow<UIState<PagingData<QuotesResponse.Result>>>(UIState.Loading)


     init {
         fetchQuotes()
     }

     fun fetchQuotes() {
         viewModelScope.launch(dispatcherProvider.main) {
             repository.getQuotes()
                 .cachedIn(viewModelScope)
                 .flowOn(dispatcherProvider.io)
                 .catch {
                     _quotesFlow.value = UIState.Error(errMsg = "Something went wrong")
                 }.collect {
                     _quotesFlow.value = UIState.Success(data = it)
                 }
         }
     }*/
}