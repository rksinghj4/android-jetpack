package com.example.lifecycleobserverdemo

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * LifecycleEventObserver doesn't have an default handling for any of events.
 * Use this If you need to access the lifecycle event directly (for example,
 * checking ON_CREATE vs ON_RESUME) otherwise DefaultLifecycleObserver is the best choice.
 *
 */
class MainActivityLifecycleEventObserver : LifecycleEventObserver {
    //Must override onStateChanged(source: LifecycleOwner, event: Lifecycle.Event).
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