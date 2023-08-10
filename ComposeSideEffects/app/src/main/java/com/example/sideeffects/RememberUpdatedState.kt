package com.example.sideeffects

import android.util.Log
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

private const val TAG = "REMEMBER_UPDATED_STATE"

@Composable
fun RememberUpdatedState() {
    val counter = remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit) {
        delay(2000)
        counter.value = 11
    }
    updateCounter(count = counter.value)
}

@Composable
fun updateCounter(count: Int) {
    val rememberUpdateState = rememberUpdatedState(newValue = count)

    LaunchedEffect(key1 = Unit) {
        Log.d(TAG, "Before Count value = ${rememberUpdateState.value}")

        delay(5000)
        /**
         * Long running task is performed only on initial composition,
         * but latest updated value can be read in side LaunchedEffect using
         * rememberUpdatedState
         */
        Log.d(TAG, "Count value = ${rememberUpdateState.value}")
    }
    Text(text = count.toString())
}

fun first() {
    Log.d(TAG, "First function is called")
}

fun second() {
    Log.d(TAG, "Second function is called")
}

@Composable
fun OnTheFlyDecisionToLandOn() {

    val state = remember {
        mutableStateOf(::first)
    }

    Button(modifier = Modifier.wrapContentSize(),
        onClick = {
            state.value = ::second
        }) {
        Text(text = "Click to change state")
    }
    LandingScreen(state.value)
}

@Composable
fun LandingScreen(onTimeOut: () -> Unit) {
    val currentTimeOutFun by rememberUpdatedState(newValue = onTimeOut)
    LaunchedEffect(key1 = Unit) {
        delay(5000)
        currentTimeOutFun()//Take the latest value if updated with in activation period (here 5 secs)
    }
}