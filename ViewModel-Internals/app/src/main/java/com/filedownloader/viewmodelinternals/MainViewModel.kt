package com.filedownloader.viewmodelinternals

import android.view.View
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel  {
    //State is hoisted in view model
    //ViewModel just exposes read only state to UI
    private val _count = mutableIntStateOf(0)
    val count = _count.asIntState()

    fun increment() {
        _count.intValue++
    }
}