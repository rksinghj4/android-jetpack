package com.raj.compose.playground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class MainScreenClickActions(
    val onBasicExample: () -> Unit = {},
    val onStateExample: () -> Unit = {},
    val onRecompositionExample: () -> Unit = {},
    val onSideEffectsSelection: () -> Unit = {},
    val onNeedOfStateHoisting: () -> Unit = {},
    val onStateHoistingExample: () -> Unit = {},
    val onStructuralEquality: () -> Unit = {}
)

@Composable
fun MainScreen(modifier: Modifier, mainScreenClickActions: MainScreenClickActions) {
    /**
     * Return a CoroutineScope bound to this point in the composition using the
     * optional CoroutineContext provided by getContext. getContext will only be called once
     * and the same CoroutineScope instance will be returned across recompositions.
     */
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = mainScreenClickActions.onBasicExample) {
            Text(text = "Basic Example")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = mainScreenClickActions.onStateExample) {
            Text(text = "State Example")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = mainScreenClickActions.onRecompositionExample) {
            Text(text = "Recomposition Example")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = mainScreenClickActions.onSideEffectsSelection) {
            Text(text = "Side Effects Selections")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = mainScreenClickActions.onNeedOfStateHoisting) {
            Text(text = "Need of State Hoisting")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = mainScreenClickActions.onStateHoistingExample) {
            Text(text = "State Hoisting")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = mainScreenClickActions.onStructuralEquality) {
            Text(text = "Structural Equality in Logcat")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}