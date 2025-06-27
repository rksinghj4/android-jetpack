package com.raj.paginationdemo.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun LoadingScreen() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.wrapContentSize(),
            color = Color.Cyan
        )
    }
}

@Composable
fun ErrorScreen(errorMsg: String) {
    Box {
        Text(text = errorMsg)
    }
}