package com.kotlinflow.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.kotlinflow.ui.theme.LearnKotlinFlowTheme
import com.kotlinflow.view.retrofit.parallel.ParallelNetworkCallsActivity
import com.kotlinflow.view.retrofit.series.SeriesNetworkCallsActivity
import com.kotlinflow.view.retrofit.single.SingleNetworkCallActivity
import com.kotlinflow.view.room.RoomTestingActivity

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
                        clickActions(context)
                    )
                }
            }
        }
    }

    private fun clickActions(context: Context) = ClickAction(
        onSingleNetworkClick = {
            SingleNetworkCallActivity.show(context)
        },
        onSeriesNetworkCallClick = {
            SeriesNetworkCallsActivity.show(context)
        },
        onParallelNetworkCallClick = {
            ParallelNetworkCallsActivity.show(context)
        },
        onRoomDBAccessClicked = {
            RoomTestingActivity.show(context)
        }
    )

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        LearnKotlinFlowTheme {
            MainScreen(ClickAction())
        }
    }
}

