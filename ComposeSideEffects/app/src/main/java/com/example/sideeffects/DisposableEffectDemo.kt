package com.example.sideeffects

import android.media.MediaPlayer
import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val TAG = "Disposable_Effect"

@Composable
fun DisposableEffectHandler() {
    val state = remember {
        mutableStateOf(false)
    }

    DisposableEffect(key1 = state.value) {
        //Must launch on initial composition even for false key
        Log.d(TAG, "Disposable Effect started")
        onDispose {  //It must be last statement in DisposableEffectScope otherwise we will get error
            /**
             * On key change cancel the current task and perform the clean up then launch again
             */
            Log.d(TAG, "Disposable Effect resource cleanup")
        }
    }

    Button(modifier = Modifier.wrapContentSize(),
        onClick = { state.value = !state.value }) {
        Text(text = "Change State")
    }
}

@Composable
fun MediaPlayerDisposable() {
    val context = LocalContext.current
    DisposableEffect(key1 = Unit) {
        Log.d(TAG, "Disposable Effect started")
        val mediaPlayer = MediaPlayer.create(context, R.raw.music1)
        mediaPlayer.start()
        onDispose {
            //Clean up
            mediaPlayer.stop()
            mediaPlayer.release()
            Log.d(TAG, "Disposable Effect resource cleanup")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeyBoardDisposable() {
    val view = LocalView.current
    val text = remember {
        mutableStateOf("")
    }
    DisposableEffect(key1 = Unit) {
        Log.d(TAG, "Disposable Effect started")

        val listener = ViewTreeObserver.OnGlobalLayoutListener {

            val insets = ViewCompat.getRootWindowInsets(view)// Gives all rectangles on screen
            // Check if keyboard is visible
            val isKeyBoardVisible = insets?.isVisible(WindowInsetsCompat.Type.ime())
            Log.d(TAG, isKeyBoardVisible.toString())
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
            Log.d(TAG, "Disposable Effect resource cleanup")
        }
    }

    TextField(
        modifier = Modifier.wrapContentSize(),
        value = text.value, onValueChange = { text.value = it }
    )
}