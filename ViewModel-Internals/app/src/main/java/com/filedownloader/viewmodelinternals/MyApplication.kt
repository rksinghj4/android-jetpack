package com.filedownloader.viewmodelinternals

import android.app.Application
import com.filedownloader.viewmodelinternals.customviewmodel.CustomViewModel

class MyApplication : Application() {

    private val map = HashMap<String, CustomViewModel>()
    override fun onCreate() {
        super.onCreate()
    }

    fun getViewModel(key: String): CustomViewModel {
        if (map[key] != null) {
            return map[key]!!
        }

        map[key] = CustomViewModel()

        return map[key]!!
    }

    fun clearViewModel() {
        map.clear()
    }

}