package com.kotlinflow.view.room

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
import androidx.compose.ui.Modifier
import com.kotlinflow.database.UserDb
import com.kotlinflow.models.UiState
import com.kotlinflow.ui.theme.LearnKotlinFlowTheme
import com.kotlinflow.view.common.CommonScreenForDatabaseUsers
import com.kotlinflow.view.common.ErrorScreen
import com.kotlinflow.view.common.LoadingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomTestingActivity : ComponentActivity() {

    private val viewModel by viewModels<RoomTestingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnKotlinFlowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState = viewModel.uiStateFlow.collectAsState()
                    val errorState = viewModel.errorSharedFlow.collectAsState(initial = false)

                    when (uiState.value) {
                        is UiState.Loading -> {
                            LoadingScreen()
                        }

                        is UiState.Success -> {
                            CommonScreenForDatabaseUsers(users = (uiState.value as UiState.Success<List<UserDb>>).data)
                        }

                        is UiState.Error -> {
                            ErrorScreen(onDismissRequest = { viewModel.dismissErrorScreen() }) {
                                viewModel.dismissErrorScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun show(context: Context) {
            Intent(context, RoomTestingActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}