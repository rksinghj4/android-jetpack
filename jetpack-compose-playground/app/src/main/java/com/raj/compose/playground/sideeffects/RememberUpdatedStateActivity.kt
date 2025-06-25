package com.raj.compose.playground.sideeffects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/*
• Splash Screen: Make a network call to decide the Next Activity
• Show Animation of 5 seconds, Network call is going on in parallel
• When the Animation ends
    • If network call succeeds, proceed with the received Next Activity from server
    • If network call fails or taking more time, proceed with the default Next Activity
 */

class RememberUpdatedStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.remember_updated_state)) {
                    var greetingMessage by remember { mutableStateOf("Greeting from client") }
                    LaunchedEffect(Unit) {
                        //greetingMessage = fetchEarlyGreetings()
                        greetingMessage = fetchDelayedGreetingsWithMoreTime()
                    }
                    RememberUpdatedStateDemo(message = greetingMessage)
                }
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, RememberUpdatedStateActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}

@Composable
fun RememberUpdatedStateDemo(message: String) {

    val context = LocalContext.current
    //In pending execution of initial  composition inside LaunchedEffect(Unit){}- On next access
    // of updatedState variable use the updated state of message.
    val updatedState by rememberUpdatedState(message)

    /**
     * In recomposition below remember will help to preserve the updated value
     * but not inside LaunchedEffect(Unit) - because code inside is executed only on initial composition
     * if we want to have the latest update value inside LaunchedEffect(Unit){} then use rememberUpdatedState
     */
    //val updatedState by remember { mutableStateOf(message) }

    LaunchedEffect(Unit) {
        /**
         * If we use remember - then updated value will not be in Toast,
         * because LaunchedEffect execution happens only on initially composition
         * at that point of time whatever value is there in updatedState we will use that in toast.
         */
        // Animation for 4 seconds
        delay(4000L)
        /**
         * Show the current updatedState message from server if it has arrived within 4 seconds
         * if not then show the default one which was initially there in updatedState
         */

        Toast.makeText(context, updatedState, Toast.LENGTH_LONG).show()
    }

}

// simulating network call to fetch the greeting from server
suspend fun fetchEarlyGreetings(): String {
    return withContext(Dispatchers.Default) {
        delay(3000L)
        return@withContext "Greeting From Server"
    }
}

// simulating network call to fetch the greeting from server
suspend fun fetchDelayedGreetingsWithMoreTime(): String {
    return withContext(Dispatchers.Default) {
        delay(6000L)
        return@withContext "Greeting From Server with delay"
    }
}