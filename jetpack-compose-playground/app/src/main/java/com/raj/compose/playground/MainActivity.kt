package com.raj.compose.playground

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.raj.compose.playground.intro.BasicComposeExampleActivity
import com.raj.compose.playground.intro.StateExampleActivity
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.app_name), content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        MainScreen(
                            modifier = Modifier,
                            clickAction(this@MainActivity)
                        )
                    }
                }
                )
            }
        }
    }

    private fun clickAction(fromActivity: Activity) = ClickActions(
        onBasicExample = {
            BasicComposeExampleActivity.show(fromActivity)
        },
        onStateExample = {
            StateExampleActivity.show(fromActivity)
        },
        onRecompositionExample = {

        },
        onSideEffectsSelection = {

        },
        onNeedOfStateHoisting = {

        },
        onStateHoistingExample = {

        }
    )
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposePlaygroundTheme {
        //Greeting("Android")
    }
}