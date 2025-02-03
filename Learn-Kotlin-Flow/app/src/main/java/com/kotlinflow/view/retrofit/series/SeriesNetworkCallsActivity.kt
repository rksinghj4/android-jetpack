package com.kotlinflow.view.retrofit.series

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.kotlinflow.models.UiState
import com.kotlinflow.models.User
import com.kotlinflow.ui.theme.LearnKotlinFlowTheme
import com.kotlinflow.view.common.CommonScreen
import com.kotlinflow.view.common.ErrorScreen
import com.kotlinflow.view.common.LoadingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesNetworkCallsActivity : ComponentActivity() {
    private val viewModel by viewModels<SeriesNetworkCallViewModel>()
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
                            CommonScreen(users = (uiState as UiState.Success<List<User>>).data)
                        }

                        is UiState.Error -> {
                            if (errorState) {
                                ErrorScreen(
                                    onDismissRequest = { viewModel.dismissErrorScreen() },
                                    onConfirmation = {
                                        viewModel.dismissErrorScreen()
                                    })
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun show(context: Context) {
            Intent(context, SeriesNetworkCallsActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}