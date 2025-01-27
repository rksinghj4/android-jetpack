package com.kotlinflow.view.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.onSecondary,
            trackColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun ErrorScreen(
    icon: ImageVector,
    title: String,
    errorDis: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(imageVector = icon, contentDescription = "Error icon")
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, text = errorDis)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = {
                onConfirmation()
            }) {
                Text(text = "Confirmation")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest.invoke() }) {
                Text(text = "Dismiss")
            }
        }
    )
}