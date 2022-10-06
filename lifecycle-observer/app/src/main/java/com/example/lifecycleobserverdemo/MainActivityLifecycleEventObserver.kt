package com.example.lifecycleobserverdemo

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MainActivityLifecycleEventObserver : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.d(TAG, "LifecycleOwner = $source Lifecycle.Event = $event")
            }
            Lifecycle.Event.ON_START -> {
                Log.d(TAG, "LifecycleOwner = $source Lifecycle.Event = $event")
            }

            Lifecycle.Event.ON_RESUME -> {
                Log.d(TAG, "LifecycleOwner = $source Lifecycle.Event = $event")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.d(TAG, "LifecycleOwner = $source Lifecycle.Event = $event")
            }
            Lifecycle.Event.ON_STOP -> {
                Log.d(TAG, "LifecycleOwner = $source Lifecycle.Event = $event")
            }
            Lifecycle.Event.ON_DESTROY -> {
                Log.d(TAG, "LifecycleOwner = $source Lifecycle.Event = $event")
            }
            Lifecycle.Event.ON_ANY -> {
                Log.d(TAG, "LifecycleOwner = $source Lifecycle.Event = $event")
            }
        }
    }
}