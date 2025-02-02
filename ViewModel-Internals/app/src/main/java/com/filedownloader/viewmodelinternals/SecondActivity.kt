package com.filedownloader.viewmodelinternals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.filedownloader.viewmodelinternals.customviewmodel.CustomViewModelActivity
import com.filedownloader.viewmodelinternals.ui.theme.ViewModelInternalsTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelInternalsTheme {
                Surface {
                    Column {
                        Text(text = "Second Activity")

                        Button(onClick = {
                            CustomViewModelActivity.show(this@SecondActivity)
                        }) {
                            Text(text = "Go back")
                        }
                    }
                }
            }
        }
    }


    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, SecondActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}