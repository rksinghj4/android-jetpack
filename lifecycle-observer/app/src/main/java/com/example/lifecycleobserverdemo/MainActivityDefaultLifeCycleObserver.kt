package com.example.lifecycleobserverdemo

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * DefaultLifecycleObserver - has default implementation for all 6 methods of FullLifecycleObserver
 * Each method is called when a specific event occur in LifecycleOwner.
 *
 * Pro:1We have to override required method only. We don't need to manually handle all methods/event.
 * 2. Less boilerplate code.
 *
 * If a class implements both DefaultLifecycleObserver interface and LifecycleEventObserver,
 * then methods of DefaultLifecycleObserver will be called first,
 * and then followed by the call of LifecycleEventObserver.onStateChanged(LifecycleOwner, Lifecycle.Event)
 *
 * If a class implements DefaultLifecycleObserver interface and in the same time uses OnLifecycleEvent,
 * then annotations will be ignored.
 *
 */
class MainActivityDefaultLifeCycleObserver : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d(TAG, "DefaultLifeCycleObserver-onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(TAG, "DefaultLifeCycleObserver-onStart")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d(TAG, "DefaultLifeCycleObserver-onResume")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d(TAG, "DefaultLifeCycleObserver-onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d(TAG, "DefaultLifeCycleObserver-onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d(TAG, "DefaultLifeCycleObserver-onDestroy")
    }
}