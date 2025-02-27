package com.raj.compose.playground.others

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In Declarative - approach just declare Composable functions,
 * framework will draw the UI based on the data coming from source.
 */
class DeclarativeActivity : ComponentActivity() {
    private lateinit var declarativeViewModel: DeclarativeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {//Bridge between Composable and non composable
            val displayText = declarativeViewModel.uiStateFlow.collectAsStateWithLifecycle()
            TopBarScaffold(stringResource(R.string.declarative_example)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        /**
                         * In declarative - Composable function will recompose themselves
                         * when there is a change in the state.
                         *
                         * In imperative - we have to set the text manually.
                         */
                        SimpleText(displayText = displayText.value)
                    }
                )
            }
        }
    }

    @Composable
    fun SimpleText(displayText: String) {
        Text(
            text = displayText,
            modifier = Modifier.padding(16.dp),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.Black
            )
        )
    }
}

class DeclarativeViewModel : androidx.lifecycle.ViewModel() {
    private val _uiStateFlow = MutableStateFlow("")
    internal val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.async {
            _uiStateFlow.value = "data from network"
        }
    }
}