package com.example.statemanagement

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * State hoisting - is a pattern of moving state out of composable to caller
 * to make composable state less.
 * Benefits:
 * 1. Single source of truth.
 * 2. Same state can be shared among multiple Composables.
 * 3. Caller can intercept the state changes.
 */
@Composable
fun StateFullCounter(modifier: Modifier = Modifier) {
    Log.d("TAG", "WaterCounter called")
    var count by rememberSaveable{
        mutableStateOf(0)
    }
    StateLessCounter(counter = count, onIncrement= { count++ }, modifier = modifier)
}

@Composable
private fun StateLessCounter(counter: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Log.d("TAG", "WaterCounter called $counter time(s)")
        if (counter > 0) {
            Text(text = "You've had $counter glasses")
        }
        Button(onClick = { onIncrement() }, enabled = counter < 10, modifier = modifier.padding(8.dp)) {
            Text(text = "Add one")
        }
    }
}