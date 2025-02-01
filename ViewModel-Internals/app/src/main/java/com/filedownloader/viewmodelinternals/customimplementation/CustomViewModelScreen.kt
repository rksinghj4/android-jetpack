package com.filedownloader.viewmodelinternals.customimplementation

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
fun CustomViewModelScreen(
    count: Int,
    increment: () -> Unit = {},
    finishAndGoNextActivity: () -> Unit = {},
    goNextActivity: () -> Unit = {}
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
            Text(text = "Increase counter")
        }

        Button(onClick = finishAndGoNextActivity) {
            //Skip the recomposition on count change
            Text(text = "Finish and Go Next")
        }

        Button(onClick = goNextActivity) {
            //Skip the recomposition on count change
            Text(text = "Without finish - Go Next")
        }
    }
}

@Preview
@Composable
fun CustomViewModelScreenPreview() {
    ViewModelInternalsTheme {
        CustomViewModelScreen(0) {}
    }
}