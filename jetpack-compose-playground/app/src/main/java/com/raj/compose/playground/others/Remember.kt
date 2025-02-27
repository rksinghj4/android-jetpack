package com.raj.compose.playground.others

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@SuppressLint("UnrememberedMutableState")
@Composable
fun CounterButtonWithoutRemember() {
    val count = mutableStateOf(0)

    Text("Counter : ${count.value}")

    Button(onClick = { count.value++ }) {
        Text("Click Me")
    }
}

@Composable
fun CounterButtonWithRemember() {
    var count by remember { mutableStateOf(0) }

    Text("Counter : $count")

    Button(onClick = { count++ }) {
        Text("Click Me")
    }
}

@Composable
fun CounterButtonWithRememberSaveable() {
    var count by rememberSaveable { mutableStateOf(0) }

    Text("Counter : $count")

    Button(onClick = { count++ }) {
        Text("Click Me")
    }
}