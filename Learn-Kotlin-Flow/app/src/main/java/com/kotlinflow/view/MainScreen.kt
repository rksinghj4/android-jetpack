package com.kotlinflow.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun MainScreen(
    onSingleNetworkClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onSingleNetworkClick.invoke() }) {
            Text(text = "Single network call")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Series network call")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Parallel network call")
        }

        Button(onClick = { /*TODO*/ }) {
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

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Reduce")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Search")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Retry")
        }
    }
}