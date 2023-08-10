package com.example.sideeffects

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private const val TAG = "LAUNCHED_EFFECT"

@Composable
fun ListComposable() {
    val categories = remember {
        mutableStateOf(emptyList<String>())
    }
    /**
     * Problem 1 - Api call is happening on main thread
     * Problem 2 - api call is getting fired on every composition/recomposition.
     */
    //categories.value = fetchCategories()

    LaunchedEffect(key1 = Unit) {
        //API call is happening on Dispatchers.Default(i.e. background thread)
        //Api will be called on initial composition and on recomposition if key changed.
        categories.value = fetchCategories()
    }

    LazyColumn {
        items(categories.value) { item ->
            Text(text = item)
        }
    }
}

private fun fetchCategories() =
    listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")

@Composable
fun Counter() {
    val counter = remember {
        mutableStateOf(0)
    }
    val isDivisibleBy5 = counter.value % 5 == 0
    LaunchedEffect(key1 = isDivisibleBy5) {
        Log.d(TAG, "Count = ${counter.value}")//Execute on initial composition and key changes
    }
    Column(
        modifier = Modifier
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(modifier = Modifier.wrapContentSize(), onClick = { counter.value++ }) {
            Text(text = "Increment count")
            Log.d(TAG, "Inc-Count = ${counter.value}") //Triggered/Recomposed on every change
        }
        Text(
            text = counter.value.toString(),
            fontSize = 36.sp,
            fontFamily = FontFamily.Cursive
        )
    }
}

@Composable
fun LaunchedEffectHandle() {
    val count = remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit) {
        // 1. Executing on Dispatchers.Default(i.e. background thread)
        // 2. On Screen rotation - LaunchedEffect leaves the composition and
        // cancel the running coroutine/task and then relaunch the coroutine again.
        // 3. On key change - cancel the running coroutine and relaunch it again.
        // 4. can't call other composable function in side coroutine/LaunchedEffect
        // 5. We don't have much control to cancel or join the coroutine here.

        try {
            for (i in 1..10) {
                count.value++
                delay(1000)
            }
        } catch (e: Exception) {
            Log.d(TAG, "LaunchedEffect Exception: ${e.message}")
        }
    }
    var text = "Counter is running ${count.value}"
    if (count.value == 10) {
        text = "Counter is stopped"
    }
    Text(text = text)
}



