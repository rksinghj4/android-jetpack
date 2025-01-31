package com.filedownloader.viewmodelinternals

import android.app.Application
import androidx.lifecycle.ViewModel

class MyApplication : Application() {

    private val map = HashMap<String, MainViewModel>()
    override fun onCreate() {
        super.onCreate()
    }

    fun getViewModel(key: String): MainViewModel {
        if (map[key] != null) {
            return map[key]!!
        }

        map[key] = MainViewModel()

        return map[key]!!
    }

    fun clearViewModel() {
        map.clear()
    }

}