package com.example.twowaydatabindingwithlivedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val user = User().apply {
        firstName = "Raj"
        lastName = "Singh"
    }

    private val quoteMutableLiveData  =  MutableLiveData<String>("Initial fact")
    val quoteLiveData
    get() = quoteMutableLiveData

    internal fun updateQuote()  {
        quoteMutableLiveData.value  = "Updated fact"
    }

    internal fun updateLastName() {
        user.lastName = "Lucky"
    }
}