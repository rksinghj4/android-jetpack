package com.raj.compose.playground.sideeffects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme

private const val TAG = "DisposableEffect"

class DisposableEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.disposable_effect)) {
                    TextFieldDemo()
                    DisposableEffectDemo()
                }
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, DisposableEffectActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}

@Composable
fun TextFieldDemo() {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        TextField(value = text, onValueChange = {
            text = it
        })
    }
}

/**
 * We can use DisposableEffect to register location listener / Activity lifecycle listener
 * or network request listener
 */
@Composable
fun DisposableEffectDemo() {
    //The CompositionLocal containing the current Compose View.
    val view = LocalView.current
    /**
     * Due to the hardcoded key - enter inside only once when composition happens
     * During recomposition we don't enter inside.
     */
    //DisposableEffect have with key and without key versions.
    //LaunchedEffect cah have 0 to 3 key or vararg keys
    //DisposableEffect cah have 0 to 3 key or vararg keys
    DisposableEffect(key1 = Unit) {//We are now in DisposableEffectScope
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            //Whenever keyboard is coming up/down the execution control comes here.
            // On first composition also control comes with isKeyboardVisible - false
            val insets: WindowInsetsCompat? = ViewCompat.getRootWindowInsets(view)

            val isKeyboardVisible = insets?.isVisible(WindowInsetsCompat.Type.ime())
            Log.d(TAG, isKeyboardVisible.toString())// keyboard up - ture, Keyboard down - false
        }
        //Registered the listener
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        /**
         * Provide onDisposeEffect to the DisposableEffect to run when it leaves the composition or its key changes.
         */
        //DisposableEffectScope has inline onDispose method which returns DisposableEffectResult
        //Provide onDisposeEffect to the DisposableEffect to run when it leaves the composition or its key changes.
        onDispose {
            //UnRegistered the listener, when going out of the activity (composition is removed from the view).
            Log.d(TAG, "onDispose called")// keyboard up - ture, Keyboard down - false
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}