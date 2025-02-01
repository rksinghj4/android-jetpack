package com.filedownloader.viewmodelinternals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.filedownloader.viewmodelinternals.customimplementation.CustomViewModelActivity
import com.filedownloader.viewmodelinternals.ui.theme.ViewModelInternalsTheme
import com.filedownloader.viewmodelinternals.utils.Utils

class MainActivity : ComponentActivity() {
    //private val viewModel by viewModels<CustomViewModel>()
    //private val myApplication = MyApplication()//Never ever create Application object
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelInternalsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { CustomViewModelActivity.show(this@MainActivity) }) {
                Text(text = "Test Custom ViewModel")
            }

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Test Custom Livedata")
            }

            Button(onClick = { Utils.showDeveloperOptionScreen(this@MainActivity) }) {
                Text(text = "Open Developer options")
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, MainActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}
