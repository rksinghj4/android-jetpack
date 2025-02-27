package com.raj.compose.playground.others

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ComposeState<T> {

    private var data: T? = null

    // list of all the composables that uses this State
    // private val composables = arrayListOf<Composable>()

    fun setValue(value: T) {
        this.data = value
        // call all the composables from here
        // Example: Heading(displayText = data)
    }

    fun getValue(): T {
        return this.data!!
    }

}

@Composable
fun stateHoistingtest() {
    val state: ComposeState<String> = ComposeState()
    Heading(displayText = state.getValue())
    state.setValue("Raj Kumar Singh")
}

@Composable
fun Heading(displayText: String) {
    Text(
        text = displayText,
        modifier = Modifier.padding(16.dp),
        style = TextStyle(
            fontSize = 20.sp,
            color = Color.Black
        )
    )
}