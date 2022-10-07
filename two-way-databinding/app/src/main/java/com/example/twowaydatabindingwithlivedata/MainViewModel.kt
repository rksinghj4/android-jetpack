package com.example.twowaydatabindingwithlivedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val quoteMutableLiveData  =  MutableLiveData<String>("Initial fact")
    val quoteLiveData
    get() = quoteMutableLiveData

    internal fun updateQuote()  {
        quoteMutableLiveData.value  = "Updated fact"
    }
}