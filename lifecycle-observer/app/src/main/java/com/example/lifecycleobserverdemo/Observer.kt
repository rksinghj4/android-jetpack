package com.example.lifecycleobserverdemo

import android.util.Log
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

//No additional library  need.
class Observer: LifecycleObserver {

    @OnLifecycleEvent(Event.ON_CREATE)
    fun onCreate() {
        Log.d(TAG, "LifecycleObserver-onCreate")
    }

    @OnLifecycleEvent(Event.ON_START)
    fun onStart() {
        Log.d(TAG, "LifecycleObserver-onStart")
    }

    @OnLifecycleEvent(Event.ON_RESUME)
    fun onResume() {
        Log.d(TAG, "LifecycleObserver-onResume")
    }

    @OnLifecycleEvent(Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG, "LifecycleObserver-onPause")
    }

    @OnLifecycleEvent(Event.ON_STOP)
    fun onStop() {
        Log.d(TAG, "LifecycleObserver-onStop")
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    fun onDestroy() {
        Log.d(TAG, "LifecycleObserver-onDestroy")
    }


}