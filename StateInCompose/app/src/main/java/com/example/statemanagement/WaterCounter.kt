package com.example.statemanagement

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/**
 * Whenever we click on "Add one" state (count) vale is changed (incremented).
 * Jetpack compose is aware of reader of the state (i.e WaterCounter)
 * so it will trigger the recomposition of reader (i.e. WaterCounter)
 * and state value will be reset to zero again
 * so we will not observe the state changes in UI
 * but in background state is getting change and recomposition is also happening.
 *
 *
 *
 * Problem here: During recomposition state value is reset.
 */
@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Log.d("TAG", "WaterCounter called")
    Column(modifier = modifier.padding(16.dp)) {
        var count: MutableState<Int> = mutableStateOf(0)
        Log.d("TAG", "WaterCounter called ${count.value} time(s)")

        Text(text = "You've had ${count.value} glasses")
        Button(onClick = { count.value++}, modifier = modifier.padding(8.dp)) {
            Text(text = "Add one")
        }
    }
}


/**
 * remember stores objects in the Composition, and forgets the object if the source location
 * where remember is called is not invoked again during a recomposition.
 */
@Composable
fun WaterCounterWithRemember(modifier: Modifier = Modifier) {
    Log.d("TAG", "WaterCounter called")
    Column(modifier = modifier.padding(16.dp)) {
        var count by remember {
            //Remember the value during recomposition, produced by composition.
            //Recomposition will always return the value produced by composition.
            mutableStateOf(0)
        }
        Log.d("TAG", "WaterCounter called ${count} time(s)")
        if (count > 0) {
            Text(text = "You've had ${count} glasses")
        }
        Button(onClick = { count++ }, enabled = count < 10, modifier = modifier.padding(8.dp)) {
            Text(text = "Add one")
        }
    }
}


@Composable
fun WaterCounterWithRememberSaveAble(modifier: Modifier = Modifier) {
    Log.d("TAG", "WaterCounter called")
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable{
            //Remember the value during recomposition, Orientation change, process or activity recreation.
            //Recomposition will always return the value produced by composition.
            mutableStateOf(0)
        }
        Log.d("TAG", "WaterCounter called $count time(s)")
        if (count > 0) {
            Text(text = "You've had $count glasses")
        }
        Button(onClick = { count++ }, enabled = count < 10, modifier = modifier.padding(8.dp)) {
            Text(text = "Add one")
        }
    }
}