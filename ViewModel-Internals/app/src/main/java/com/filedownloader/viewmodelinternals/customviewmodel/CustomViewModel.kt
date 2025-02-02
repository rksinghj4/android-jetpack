package com.filedownloader.viewmodelinternals.customviewmodel

import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CustomViewModel {
    val livedata = MutableLiveData<String>("")
    //State is hoisted in view model
    //ViewModel just exposes read only state to UI
    private val _count = mutableIntStateOf(0)
    val count = _count.asIntState()

    fun increment() {//Write to state must perform inside ViewModel
        _count.intValue++
    }
}



