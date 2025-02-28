package com.raj.compose.playground.sideeffects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.res.stringResource
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme

class WhatAreSideEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.what_are_side_effects)) {
                    WhatAreSideEffects()
                }
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, WhatAreSideEffectActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}