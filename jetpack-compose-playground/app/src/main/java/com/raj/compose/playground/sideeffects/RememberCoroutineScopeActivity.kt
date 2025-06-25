package com.raj.compose.playground.sideeffects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RememberCoroutineScopeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                RememberCoroutineScopeExample()
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, RememberCoroutineScopeActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}

fun normalFun1(coroutineScope: CoroutineScope) {
    //We just need a scope to launch the coroutine
    coroutineScope.launch { }
}

fun normalFun2(coroutineScope: CoroutineScope) {
    //We just need a scope to launch the coroutine
    coroutineScope.async { }
}

@Composable
fun RememberCoroutineScopeExample() {

    val snackbarHostState = remember {
        //State of the SnackbarHost, which controls the queue
        // and the current Snackbar being shown inside the SnackbarHost.
        SnackbarHostState()
    }

    /**
     * Return a CoroutineScope bound to this point in the composition
     * using the optional CoroutineContext provided by getContext.
     * getContext will only be called once and the same CoroutineScope instance
     * will be returned across recompositions.
     *
     * This scope will be cancelled when this call leaves the composition.
     */
    val scope = rememberCoroutineScope()
    val snackBarText = stringResource(R.string.remember_coroutine_scope)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {//Normal function
                //Perform the click and leave the screen. Snack bar should go away immediately.
                //Perform the multiple click and you will see the queued events.
                scope.launch {//rememberCoroutineScope Best part - It owns the lifecycle of composition
                    //showSnackbar is suspend function - can be called from another suspend or coroutine
                    snackbarHostState.showSnackbar(snackBarText)//Actionable icon
                }
                /**
                 * A Composable can be called inside another composable.
                 * rememberCoroutineScope and LaunchedEffect both are composable
                 * so can't be called here directly.
                 */
                //GlobalScope.launch {} - Will not own the lifecycle. So never use inside onClick
            }) {
                Text(text = stringResource(R.string.show_snackbar))
            }
        }
    }
    /**
     * Error: Calls to launch/async should happen inside a LaunchedEffect and not inside composition.
     * Because during recomposition we will endup launching unnecessary coroutines.
     * So never ever allowed to launch coroutine inside @Composable functions.
     */
    //scope.launch {  }
}

