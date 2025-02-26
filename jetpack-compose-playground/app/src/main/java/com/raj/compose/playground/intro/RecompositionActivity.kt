package com.raj.compose.playground.intro

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme

class RecompositionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.recomposition)) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Recomposition()
                    }
                }
            }
        }
    }

    @Composable
    fun Recomposition() {
        Log.d(TAG, "Recomposition - Start")
        var count by remember {
            Log.d(TAG, "Recomposition - remember")
            mutableStateOf(0)
        }
        Button(
            onClick = { count++ },
            content = {
                Text("Count: $count")
                Log.d(TAG, "Recomposition - Content End")
            }
        )
    }


    companion object {
        const val TAG = "RecompositionDemo"
        fun show(fromActivity: Activity) {
            Intent(fromActivity, RecompositionActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}

