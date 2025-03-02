package com.raj.compose.playground.sideeffects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProducedStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.produce_state)) {
                    ProduceStateExample("ayz.com")
                }
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, ProducedStateActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}


@Composable
fun ProduceStateExample(url: String) {

    val networkResultState: State<NetworkResult> = loadDataFromNetwork(url)

    when (networkResultState.value) {
        is NetworkResult.Success -> {
            ShowText((networkResultState.value as NetworkResult.Success).data)
        }

        is NetworkResult.Loading -> {
            ShowText("Loading...")
        }

        is NetworkResult.Error -> {
            ShowText("Error")
        }
    }

}

@Composable
fun ShowText(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}

@Composable
fun loadDataFromNetwork(
    url: String, networkService: NetworkService = NetworkService()
): State<NetworkResult> {
    /**
     * produceState is used to convert non-compose state(NetworkResult) to compose state
     * 1. producer is launched when produceState enters the composition and is cancelled
     * when produceState leaves the composition.
     * 2.  If key1 or key2 change, a running producer will be cancelled
     * and re-launched for the new source.
     * 3. producer should use ProduceStateScope.value to set new values on the returned State.
     * 4. produceState may be used to observe either suspending or non-suspending sources of external data,
     * 5. The returned State conflates values; no change will be observable
     * if new value is not distinct from previous one. (produceState is optimized)
     * 6. Under the hood uses LaunchedEffect, with keys (0 to 3 keys, or vararg)
     */
    return produceState<NetworkResult>(
        initialValue = NetworkResult.Loading,
        key1 = url, key2 = networkService
    ) {
        val data = networkService.fetch(url)
        value = if (data.isEmpty()) {
            NetworkResult.Error
        } else {
            NetworkResult.Success(data)
        }
    }
}

sealed interface NetworkResult {

    data class Success(val data: String) : NetworkResult

    object Error : NetworkResult

    object Loading : NetworkResult

}

class NetworkService {

    suspend fun fetch(url: String): String {
        return withContext(Dispatchers.IO) {
            delay(3000L)
            return@withContext "Data from Network"
        }
    }

}
