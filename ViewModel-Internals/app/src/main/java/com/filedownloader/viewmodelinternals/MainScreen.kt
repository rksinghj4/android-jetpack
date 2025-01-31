package com.filedownloader.viewmodelinternals

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.filedownloader.viewmodelinternals.ui.theme.ViewModelInternalsTheme

const val TAG = "TEST"

@Composable
fun MainScreen(
    count: Int,
    increment: () -> Unit = {},
    nextActivity: () -> Unit = {}
) {
    //Stateless composable
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Not recompose when count changes")//Skip the recomposition on count change

        Text(text = "$count")

        Button(onClick = {
            increment.invoke()
        }) {
            Text(text = "Click me")
        }

        Button(onClick = nextActivity) {
            //Skip the recomposition on count change
            Text(text = "Go to next")
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    ViewModelInternalsTheme {
        MainScreen(0) {}
    }
}