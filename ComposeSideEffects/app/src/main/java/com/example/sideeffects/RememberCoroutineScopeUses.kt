package com.example.sideeffects

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "LAUNCHED_EFFECT"

@Composable
fun RememberCoroutineScope() {
    val count = remember {
        mutableStateOf(0)
    }

    /**
     * 1. Scope is associated with composition. If this composition is going away from screen then
     * scope will get cancelled so does all it's coroutines.
     * Then on recomposition a new scope object will be created.
     * 2. We have control to cancel the coroutine.
     * 3. In event handler we can use this rememberCoroutineScope to launch the coroutine.
     */
    val scope = rememberCoroutineScope()

    Column {
        Button(onClick = {
            // 1. On Click - It doesn't cancel the running coroutine if any
            // but it additionally launch a new one.
            // 2. Executing on Dispatchers.Default(i.e. background thread)
            // 3. On Screen rotation - composable function leaves the composition and
            // cancel it's scope and so does all its coroutines and then on next click relaunch
            // the coroutine with newly created coroutine scope.
            // 4. can't call other composable function in side coroutine/onClick.
            // 5. We have control to cancel or join the coroutine here because of scope.
            scope.launch {
                try {
                    for (i in 1..10) {
                        count.value++
                        delay(1000)
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "LaunchedEffect Exception: ${e.message}")
                }
            }
        }) {
            Text(text = "Start")
        }

        var text = "Counter is running ${count.value}"
        if (count.value % 10 == 0) {
            text = "Counter is stopped"
        }
        Text(text = text)
    }
}