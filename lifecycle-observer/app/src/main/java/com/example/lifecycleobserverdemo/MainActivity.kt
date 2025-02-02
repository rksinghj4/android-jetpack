package com.example.lifecycleobserverdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * We can add multiple observers here, they will trigger in the order they are added.
         */
        lifecycle.addObserver(Observer())
        lifecycle.addObserver(MainActivityDefaultLifeCycleObserver())//Best choice, because of default implementation
        lifecycle.addObserver(MainActivityLifecycleEventObserver())
        Log.d(TAG, "MainActivity-onCreate")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "MainActivity-onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity-onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity-onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity-onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity-onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity-onDestroy")
    }


}