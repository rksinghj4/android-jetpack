package com.raj.compose.playground

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raj.compose.playground.compositionlocal.StructuralEqualityPolicyTest
import com.raj.compose.playground.intro.BasicComposeExampleActivity
import com.raj.compose.playground.intro.RecompositionActivity
import com.raj.compose.playground.intro.StateExampleActivity
import com.raj.compose.playground.ui.SideEffectSelectionActivity
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {

    val mainViewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val structuralEquality by mainViewModel.structuralEqualityFlow.collectAsStateWithLifecycle()
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.app_name), content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        MainScreen(
                            modifier = Modifier,
                            clickAction(this@MainActivity)
                        )
                        if (structuralEquality) {
                            StructuralEqualityPolicyTest()
                        }
                    }
                }
                )
            }
        }
    }

    private fun clickAction(fromActivity: Activity) = MainScreenClickActions(
        onBasicExample = {
            BasicComposeExampleActivity.show(fromActivity)
        },
        onStateExample = {
            StateExampleActivity.show(fromActivity)
        },
        onRecompositionExample = {
            RecompositionActivity.show(fromActivity)
        },
        onSideEffectsSelection = {
            SideEffectSelectionActivity.show(fromActivity)
        },
        onNeedOfStateHoisting = {

        },
        onStateHoistingExample = {

        },
        onStructuralEquality = {
            mainViewModel.setStructuralEqualityFlow()
        }
    )
}

class MainViewModel : androidx.lifecycle.ViewModel() {
    private val _structuralEqualityFlow = MutableStateFlow<Boolean>(false)
    val structuralEqualityFlow = _structuralEqualityFlow.asStateFlow()

    fun setStructuralEqualityFlow() {
        _structuralEqualityFlow.value = true
    }
}
