package com.raj.compose.playground.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raj.compose.playground.R

data class SideEffectClickActions(
    val onWhatAreSideEffect: () -> Unit = {},
    val onLaunchedEffect: () -> Unit = {},
    val onRememberCoroutineScope: () -> Unit = {},
    val onDisposableEffect: () -> Unit = {},
    val onSideEffect: () -> Unit = {},
    val onDerivedState: () -> Unit = {},
    val onRememberUpdateState: () -> Unit = {},
    val onProduceState: () -> Unit = {},
    val onSnapShotFlow: () -> Unit = {},
)

@Composable
fun SideEffectSelectionScreen(clickActions: SideEffectClickActions) {
    Button(onClick = clickActions.onWhatAreSideEffect) {
        Text(text = stringResource(R.string.what_are_side_effects))
    }

    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = clickActions.onLaunchedEffect) {
        Text(text = stringResource(R.string.launched_effect))
    }

    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = clickActions.onRememberCoroutineScope) {
        Text(text = stringResource(R.string.remember_coroutine_scope))
    }

    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = clickActions.onDisposableEffect) {
        Text(text = stringResource(R.string.disposable_effect))
    }

    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = clickActions.onSideEffect) {
        Text(text = stringResource(R.string.side_effect))
    }

    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = clickActions.onDerivedState) {
        Text(text = stringResource(R.string.derived_state))
    }

    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = clickActions.onRememberUpdateState) {
        Text(text = stringResource(R.string.remember_updated_state))
    }

    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = clickActions.onProduceState) {
        Text(text = stringResource(R.string.produce_state))
    }

    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = clickActions.onSnapShotFlow) {
        Text(text = stringResource(R.string.snapshot_flow))
    }
}