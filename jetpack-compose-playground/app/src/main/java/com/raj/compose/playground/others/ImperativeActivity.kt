package com.raj.compose.playground.others

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.raj.compose.playground.R
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ImperativeActivity : AppCompatActivity() {
    lateinit var imperativeViewModel: ImperativeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imperative)
        imperativeViewModel = ImperativeViewModel()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    imperativeViewModel.uiStateFlow.collect {
                        /**
                         * In declarative - Composable function will recompose themselves
                         * when there is a change in the state.
                         *
                         * In imperative - we have to set the text manually.
                         */
                        findViewById<TextView>(R.id.dataToDisplay).text = it
                    }
                }
            }

            insets
        }

    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, ImperativeActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}

class ImperativeViewModel : androidx.lifecycle.ViewModel() {
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