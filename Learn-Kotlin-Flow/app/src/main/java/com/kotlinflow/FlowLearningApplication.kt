package com.kotlinflow

import android.app.Application
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlowLearningApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
    }
}