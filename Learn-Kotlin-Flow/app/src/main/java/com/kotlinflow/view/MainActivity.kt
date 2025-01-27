package com.kotlinflow.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import com.kotlinflow.models.UiState
import com.kotlinflow.ui.theme.LearnKotlinFlowTheme
import com.kotlinflow.view.common.ErrorScreen
import com.kotlinflow.view.common.LoadingScreen
import com.kotlinflow.view.retrofit.single.SingleNetworkCallActivity

class MainActivity : ComponentActivity() {


    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnKotlinFlowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current

                    MainScreen(
                        onSingleNetworkClick = {
                            Intent(context, SingleNetworkCallActivity::class.java).also {
                                context.startActivity(it)
                            }
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun Screen() {
        val context = LocalContext.current

        val uiState by viewModel.uiStateFlow.collectAsState()
        val errorSharedState by viewModel.errorSharedFlow.collectAsState(initial = false)

        when (uiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }

            is UiState.Success -> {
                MainScreen(
                    onSingleNetworkClick = {
                        Intent(context, SingleNetworkCallActivity::class.java).also {
                            context.startActivity(it)
                        }
                    }
                )
            }

            else -> {
                //Intentionally blank
            }
        }

        if (errorSharedState) {
            ErrorScreen(
                icon = Icons.Default.Info,
                title = "Error",
                errorDis = "Error message",
                onDismissRequest = {
                    viewModel.dismissErrorDialog()
                },
                onConfirmation = {
                    viewModel.dismissErrorDialog()
                }
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        LearnKotlinFlowTheme {
            MainScreen()
        }
    }
}

