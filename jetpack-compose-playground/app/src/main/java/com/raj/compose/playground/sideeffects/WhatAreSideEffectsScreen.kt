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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raj.compose.playground.R

data class StringHolder(var str: String = "")

private const val TAG = "WhatAreSideEffects"

@Composable
fun WhatAreSideEffects() {
    Log.d(TAG, "Beginning of  - WhatAreSideEffects")
    val context = LocalContext.current

    val stringHolder = remember(LocalConfiguration.current) {
        //Here @DisallowComposableCalls
        Log.d(TAG, "remember - StringHolder")
        StringHolder()
    }.apply {
        //It executes every time
        this.str = stringResource(R.string.what_are_side_effects)
        Log.d(TAG, "apply - stringResource")
    }

    //val toastText = stringResource(R.string.what_are_side_effects)//Optimize it by remembering
    val toastText = stringHolder.str
    /**
     * makeText() and show() both are non composable and compose framework
     * doesn't have control over non composable functions to optimize them.
     *
     * on every click, counter increases and WhatAreSideEffects() gets recompose in older BOM and current also
     *
     *     implementation(platform("androidx.compose:compose-bom:2023.03.00"))
     *So to handle side effect we need to use
     */

    // Not reading state data still called on state chane
    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()

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