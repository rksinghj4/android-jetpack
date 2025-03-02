package com.raj.compose.playground.sideeffects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.stringResource
import com.raj.compose.playground.R
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
        },
        onRememberCoroutineScope = {
            RememberCoroutineScopeActivity.show(fromActivity)
        },
        onDisposableEffect = {
            DisposableEffectActivity.show(fromActivity)
        },
        onSideEffect = {
            SideEffectActivity.show(fromActivity)
        },

        onRememberUpdateState = {
            RememberUpdatedStateActivity.show(fromActivity)
        },
        onDerivedState = {
            DerivedStateActivity.show(fromActivity)
        },
        onProduceState = {

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

