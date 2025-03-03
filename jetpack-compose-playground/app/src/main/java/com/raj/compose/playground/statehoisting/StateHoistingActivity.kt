package com.raj.compose.playground.statehoisting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold

class StateHoistingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopBarScaffold(title = stringResource(R.string.state_hoisting)) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.state_hoisted_in_parent)
                )
                StateHoistingExample()
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, StateHoistingActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}

/**
 * If we want to observe change in the counter here in NeedOfStateHoistingTextContent
 * then hoist the state in parent composable.
 * Who wants to change the shared state will send the event up to the parent, updates happens here.
 * After the sate change parent/compose framework will send the updated state to interested children
 */

@Composable
fun StateHoistingExample() {
    var counter by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            StateHoistingButtonContent { counter++ }
            StateHoistingTextContent(counter)
        })
}

@Composable
fun StateHoistingButtonContent(onCounterChange: () -> Unit) {
    Button(onClick = { onCounterChange() }) {
        Text(text = stringResource(R.string.click_here))
    }
}

@Composable
fun StateHoistingTextContent(counter: Int) {
    Text(text = "$counter", modifier = Modifier.padding(20.dp))
}