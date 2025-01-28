package com.kotlinflow

import android.app.Application
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlowLearningApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}