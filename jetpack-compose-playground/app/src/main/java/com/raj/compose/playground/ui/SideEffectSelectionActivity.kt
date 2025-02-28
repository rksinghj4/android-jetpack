package com.raj.compose.playground.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.raj.compose.playground.R
import com.raj.compose.playground.sideeffects.LaunchedEffectActivity
import com.raj.compose.playground.sideeffects.WhatAreSideEffectActivity
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme

class SideEffectSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.select_side_effect))
                 {
                    SideEffectSelectionScreen(clickActions = clickActions(this@SideEffectSelectionActivity))
                }
            }
        }

    }

    private fun clickActions(fromActivity: Activity) = SideEffectClickActions(
        onWhatAreSideEffect = {
            WhatAreSideEffectActivity.show(fromActivity)
        },
        onLaunchedEffect = {
            LaunchedEffectActivity.show(fromActivity)
        }
    )

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, SideEffectSelectionActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}

