package com.kotlinflow.view

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
import androidx.compose.ui.tooling.preview.Preview
import com.kotlinflow.ui.theme.LearnKotlinFlowTheme
import com.kotlinflow.view.common.ErrorScreen
import com.kotlinflow.view.common.LoadingScreen

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
                    Screen()
                }
            }
        }
    }

    @Composable
    fun Screen() {
        val showLoadingIndicator by viewModel.showLoadingIndicatorStateFlow.collectAsState()
        val errorSharedState by viewModel.errorSharedFlow.collectAsState(initial = true)

        when {
            showLoadingIndicator -> {
                LoadingScreen()
            }

            errorSharedState -> {
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

            else -> {
                MainScreen(
                    onSingleNetworkClick = {

                    }
                )
            }
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

