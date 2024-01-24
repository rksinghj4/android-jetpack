package com.example.bottomandtopappbardemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DashboardScreen() {
    Box {
        Text(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Center),
            text = "Hey you are on Dashboard"
        )
    }
}