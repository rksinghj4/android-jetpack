package com.raj.compose.playground.sideeffects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme

class SideEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.side_effect)) {
                    SideEffectDemo()
                }
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, SideEffectActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}

@Composable
fun SideEffectDemo() {

    var counter by remember { mutableStateOf(0) }
    val compositionCounter = remember { CompositionCounter(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { counter++ }) {
            Text(text = stringResource(R.string.click_here))
        }
        Text(text = "$counter", modifier = Modifier.padding(20.dp))
    }
    //Layout Inspector uses SideEffect to calculate number of (re)composition
    //Many library uses SideEffect to calculate number of (re)composition
    //SideEffect are generally used for debugging purpose.
    //DisposableEffect cah have 0 to 3 key or vararg keys
    SideEffect {//Note: SideEffect has 0 key only version, no other version,
        /**
         * No matter were we locate it inside composable function (i.e SideEffectDemo)
         * Execution control Come here after every successful composition.
         *
         * Due to snapshot state change - currently running recomposition might be canceled/invalidated
         * and recomposition might start again with the latest snapshot.
         * In that case SideEffect
         */
        compositionCounter.count++
        Log.d("SideEffectExample", "SideEffect block compositionCounter : $compositionCounter")
    }

    Log.d("SideEffectExample", "Recomposition")
}

data class CompositionCounter(var count: Int)