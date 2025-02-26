package com.raj.compose.playground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.unit.dp

data class ClickActions(
    val onBasicExample: () -> Unit = {},
    val onStateExample: () -> Unit = {},
    val onRecompositionExample: () -> Unit = {},
    val onSideEffectsSelection: () -> Unit = {},
    val onNeedOfStateHoisting: () -> Unit = {},
    val onStateHoistingExample: () -> Unit = {},
)

@Composable
fun MainScreen(modifier: Modifier, clickActions: ClickActions) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = clickActions.onBasicExample) {
            Text(text = "Basic Example")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = clickActions.onStateExample) {
            Text(text = "State Example")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = clickActions.onRecompositionExample) {
            Text(text = "Recomposition Example")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = clickActions.onSideEffectsSelection) {
            Text(text = "Side Effects Selections")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = clickActions.onNeedOfStateHoisting) {
            Text(text = "Need of State Hoisting")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = clickActions.onStateHoistingExample) {
            Text(text = "State Hoisting")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}