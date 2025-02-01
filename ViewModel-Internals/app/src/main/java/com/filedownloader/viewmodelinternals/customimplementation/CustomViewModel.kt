package com.filedownloader.viewmodelinternals.customimplementation

import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableIntStateOf

class CustomViewModel {
    //State is hoisted in view model
    //ViewModel just exposes read only state to UI
    private val _count = mutableIntStateOf(0)
    val count = _count.asIntState()

    fun increment() {//Write to state must perform inside ViewModel
        _count.intValue++
    }
}



