package com.kotlinflow.view.retrofit.single

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.kotlinflow.models.User
import com.kotlinflow.models.UiState
import com.kotlinflow.ui.theme.LearnKotlinFlowTheme
import com.kotlinflow.view.common.ErrorScreen
import com.kotlinflow.view.common.LoadingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleNetworkCallActivity : ComponentActivity() {

    private val viewModel by viewModels<SingleNetworkCallViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LearnKotlinFlowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.uiStateFlow.collectAsState()
                    val errorState by viewModel.errorSharedFlow.collectAsState(initial = false)
                    when (uiState) {
                        is UiState.Loading -> {
                            LoadingScreen()
                        }

                        is UiState.Success -> {
                            SingleNetworkScreen((uiState as UiState.Success<List<User>>).data)
                        }

                        is UiState.Error -> {
                            if (errorState) {
                                ErrorScreen(
                                    icon = Icons.Default.Info,
                                    title = "Error",
                                    errorDis = "Error message",
                                    onDismissRequest = {
                                        viewModel.dismissErrorScreen()
                                    },
                                    onConfirmation = {
                                        viewModel.dismissErrorScreen()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


