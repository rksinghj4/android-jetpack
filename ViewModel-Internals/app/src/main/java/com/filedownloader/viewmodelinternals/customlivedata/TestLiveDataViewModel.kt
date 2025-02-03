package com.filedownloader.viewmodelinternals.customlivedata

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class TestLiveDataViewModel() : ViewModel() {
    private val color = listOf(
        Color.Red,
        Color.Cyan,
        Color.Black,
        Color.Blue,
        Color.Green,
        Color.Magenta
    )
    //internal var colorLivedata = MutableLiveDataWithoutLifecycle(Color.Cyan)

    internal var colorLivedata = MutableLiveDataWithLifecycle(Color.Cyan)
        private set

    internal val singleLiveEvent = SingleLiveEvent<String>()

    fun updateToSingleLiveEvent() {
        val stringList = listOf<String>("AA", "BB", "CC", "DD")
        singleLiveEvent.value = stringList[Random.nextInt(stringList.size - 1)]
    }

    fun updateLiveDataOnMainThread() {
        colorLivedata.setValue(color[Random.nextInt(color.size)])
    }

    fun postValueFromBackgroundThread() {
        viewModelScope.launch(Dispatchers.IO) {
            colorLivedata.postValue(color[Random.nextInt(color.size)])
        }
    }
}