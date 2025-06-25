package com.raj.compose.playground.sideeffects

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raj.compose.playground.R

private const val TAG = "LaunchedEffectScreen"

@Composable
fun LaunchedEffectScreen() {
    Log.d(TAG, "Beginning of  - LaunchedEffectScreen")

    val context = LocalContext.current
    val toastText = stringResource(R.string.launched_effect)

    /**
     * makeText() and show() both are non composable and compose framework
     * doesn't have control over non composable functions to optimize them.
     *
     * on every click, counter increases and WhatAreSideEffects() gets recompose in older BOM
     *
     *     implementation(platform("androidx.compose:compose-bom:2023.03.00"))
     *So to handle side effect we need to use
     */
    //Composable can be called from another composable
    //DisposableEffect can have 0 to 3 key or vararg keys
    //LaunchedEffect can have 0 to 3 key or vararg keys
    LaunchedEffect(key1 = true) {//Compose framework has control on this
        //@Composable invocations can only happen from the context of a @Composable function
        //remember {  } //Error

        //Non composable function or suspendable api call can be placed here

        fetchData() //suspend function
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()//Normal function

    }

    DisposableEffect(key1 = true) {
        //Error: Suspend function 'fetchData' should be called only from a coroutine or another suspend function
        //fetchData()

        onDispose {//It must be last

        }
    }

    var counter by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { counter++ }) {
            Text(text = stringResource(R.string.click_here))
        }
        Text(text = "$counter", modifier = Modifier.padding(20.dp))
    }
}

suspend fun fetchData() = "fetch Data"