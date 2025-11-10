package com.kotlinflow.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

data class ClickAction(
    val onSingleNetworkClick: () -> Unit = {},
    val onSeriesNetworkCallClick: () -> Unit = {},
    val onParallelNetworkCallClick: () -> Unit = {},
    val onRoomDBAccessClicked: () -> Unit = {}
)

@Composable
fun MainScreen(
    clickAction: ClickAction
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            clickAction.onSingleNetworkClick.invoke()
        }) {
            Text(text = "Single network call")
        }

        Button(onClick = {
            clickAction.onSeriesNetworkCallClick.invoke()
        }) {
            Text(text = "Series network call")
        }

        Button(onClick = {
            clickAction.onParallelNetworkCallClick.invoke()
        }) {
            Text(text = "Parallel network call")
        }

        Button(onClick = { clickAction.onRoomDBAccessClicked.invoke() }) {
            Text(text = "Room database lib")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Catch error")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Emit all error")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Completion")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Long running task")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Two long running task")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Filter")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Map")
        }

        Button(onClick = {
            coroutineScope.launch {
                zipTest()
            }
        }) {
            //Text(text = "Reduce")
            Text(text = "zip Test")

        }

        Button(onClick = {
            coroutineScope.launch {
                combineTest()
            }
        }) {
            //Text(text = "Search")
            Text(text = "Combine Test")
        }

        Button(onClick = {
            coroutineScope.launch {
                mergeTest()
            }
        }) {
            //Text(text = "Retry")
            Text(text = "Merge Test")
        }
    }
}

suspend fun zipTest() {
    flow<String> {
        repeat(5) {
            delay(1000)
            emit("A - $it")
        }
    }.zip(flow<Int> {
        repeat(5) {
            emit("$it".toInt())
        }
    }) { a, b ->
        "$a - $b"
    }.collect {
        Log.d("ZIP_TEST", it)
    }
}

suspend fun  combineTest() {
    flow<String> {
        repeat(5) {
            delay(1000)
            emit("A - $it")
        }
    }.combine(flow<Int> {
        repeat(5) {
            delay(1000)
            emit("$it".toInt())
        }
    }) { a, b ->
        "$a - $b"
    }.collect {
        Log.d("COMBINE_TEST", it)
    }
}

suspend fun mergeTest() {
    merge(
        flow<String> {
            repeat(5) {
                delay(1000)
                emit("A - $it")
            }
        },
        flow<Int> {
            repeat(5) {
                emit("$it".toInt())
            }
        }).collect {
        Log.d("MERGE_TEST", it.toString())
    }
}