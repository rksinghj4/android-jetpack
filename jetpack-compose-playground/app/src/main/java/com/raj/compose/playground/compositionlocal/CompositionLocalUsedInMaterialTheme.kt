package com.raj.compose.playground.compositionlocal

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MaterialThemeDemo() {
    MaterialTheme {
        Column {
            MaterialTextOne(displayText = "TextOne")
            MaterialDisplayOtherTexts()
        }
    }
}

@Composable
fun MaterialDisplayOtherTexts() {
    MaterialTextTwo(displayText = "TextTwo")
    MaterialTextThree(displayText = "TextThree")
}

@Composable
fun MaterialTextOne(displayText: String) {
    Text(
        text = displayText,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun MaterialTextTwo(displayText: String) {
    Text(
        text = displayText,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun MaterialTextThree(displayText: String) {
    Text(
        text = displayText,
        color = MaterialTheme.colorScheme.secondary
    )
}

