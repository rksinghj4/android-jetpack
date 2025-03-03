package com.raj.compose.playground.sideeffects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class SnapshotFlowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.snapshot_flow)) {
                    SnapshotFlowScrollToTopExample()
                }
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, SnapshotFlowActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}


@Composable
fun SnapshotFlowScrollToTopExample() {

    val listState = rememberLazyListState()

    var isEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        ///Same optimization can we done using derivedStateOf {}
        snapshotFlow {
            //For first 21 items it will be evaluated to false.
            listState.firstVisibleItemIndex > 20
        }.distinctUntilChanged()//consecutive same values are not allowed to go down stream
            .collect {//Distinct consecutive values will be reached here and collected
                isEnabled = it
                Log.d("SnapshotFlowScrollToTopExample", "collect: $it")
            }
    }
    Column {
        Column(modifier = Modifier.fillMaxHeight(.85f)) {
            Row {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(.4f),
                    state = listState
                ) {
                    items((1..100).toList()) {
                        Text(
                            "Item $it",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth(.1f))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "snapshotFlow{} is used to convert something to Flow to take leverage of flow operators"
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color.LightGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { }, enabled = isEnabled, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Scroll To Top")
            }
        }
    }
}

@Composable
fun SnapshotFlowExample() {

    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items((1..100).toList()) {
            Text(
                "Item $it",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> index > 0 }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                // logs when the user scrolls past the first item
                Log.d("SnapshotFlowExample", "collect: $it")
            }
    }

}



