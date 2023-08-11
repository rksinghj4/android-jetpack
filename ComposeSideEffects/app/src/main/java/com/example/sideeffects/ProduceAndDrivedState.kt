package com.example.sideeffects

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ProduceStateMimic() {
    /**
     * Defined a state object
     */
    val state = remember {
        mutableStateOf(0)
    }

    /**
     * 1. Updating state asynchronously
     */
    LaunchedEffect(Unit) {
        for (i in 1..10) {
            delay(1000)
            state.value++
        }
    }

    Text(
        text = state.value.toString(),
        modifier = Modifier.wrapContentSize(),
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun ProduceState() {
    /**
     * Here we have combined above two things definition of state and asynchronous updates to it.
     */
    val state = produceState(initialValue = 0) {
        //Inside produceState we can observe livedata or flow
        for (i in 1..10) {
            delay(1000)
            value++ //here we can access state by using value property
        }
    }


    Text(
        text = state.value.toString(),
        modifier = Modifier.wrapContentSize(),
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun LoadingUsingProduceState() {
    val degree = produceState(initialValue = 0) {
        while (true) {
            delay(16)
            value = (value + 20 ) % 360
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(1f),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .rotate(degrees = degree.value.toFloat()),
                imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
            
            Text(text = "Loading")
        }
    }
}

@Composable
fun DerivedStateMimic() {
    val mState = remember {
        mutableStateOf(5)
    }
    val pState = produceState(initialValue = 1) {
        //Inside produceState we can observe livedata or flow
        for (i in 1..10) {
            delay(1000)
            value++ //here we can access state by using value property
        }
    }

    /*val dState = derivedStateOf {

    }*/

    Text(
        text = "${mState.value} * ${pState.value} = ${mState.value * pState.value}",
        modifier = Modifier.wrapContentSize(),
        style = MaterialTheme.typography.headlineLarge
    )
}


@Composable
fun DerivedState() {
    val mState = remember {
        mutableStateOf(5)
    }
    val pState = produceState(initialValue = 1) {
        for (i in 1..10) {
            delay(1000)
            value++ //here we can access state by using value property
        }
    }

    Text(
        text = "${mState.value} * ${pState.value} = ${mState.value * pState.value}",
        modifier = Modifier.wrapContentSize(),
        style = MaterialTheme.typography.headlineLarge
    )
}