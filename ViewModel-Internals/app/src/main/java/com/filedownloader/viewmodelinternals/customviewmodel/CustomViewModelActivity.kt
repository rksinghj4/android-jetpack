package com.filedownloader.viewmodelinternals.customviewmodel

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.filedownloader.viewmodelinternals.MyApplication
import com.filedownloader.viewmodelinternals.SecondActivity
import com.filedownloader.viewmodelinternals.ui.theme.ViewModelInternalsTheme
import com.filedownloader.viewmodelinternals.utils.Utils.isDoNotKeepActivityOptionON

class CustomViewModelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelInternalsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel =
                        (application as MyApplication).getViewModel(CustomViewModel::class.java.name) //

                    //TestLocalHoisting()
                    TestViewModel(viewModel)
                    //TestViewModelWithLocalCreate()
                }
            }
        }
    }


    @Composable
    fun TestViewModel(viewModel: CustomViewModel) {
        val count = viewModel.count
        CustomViewModelScreen(count = count.intValue, increment = {
            viewModel.increment()
            Log.e(TAG, "count = ${count.intValue}")
        }, finishAndGoNextActivity = {
            SecondActivity.show(this)
            finish() // Permanently destroy the activity and clear the view model.
        }) {
            /**
             * If (Don't keep activity is on) -> permanently destroy the activity
             * else keep in back stack.
             */

            /**
             * If (Don't keep activity is on) -> permanently destroy the activity
             * else keep in back stack.
             */

            SecondActivity.show(this)

            Log.d(TAG, "isDoNotKeepActivityIsOn: ${isDoNotKeepActivityOptionON(this)}")
        }
    }

    @Composable
    fun TestViewModelWithLocalCreate() {
        Log.d(TAG, "TestViewModelWithLocalCreate called on every change of viewModel.count")
        //On every recomposition new object is created.So always see initial value 0
        val viewModel = CustomViewModel()
        CustomViewModelScreen(count = viewModel.count.intValue, increment = {
            viewModel.increment()
            Log.e(TAG, "count = ${viewModel.count.intValue}")
        }) {
            SecondActivity.show(this)
        }
    }

    @Composable
    fun TestLocalHoisting() {//Stateful composable
        val count = remember {
            mutableIntStateOf(0)//MutableIntState
        }
        CustomViewModelScreen(count = count.intValue, increment = {
            count.intValue++
            Log.e(TAG, "count = ${count.intValue}")
        }) {
            SecondActivity.show(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onDestroy() {
        //onDestroy() - is called for both configurations changes
        // and when activity is permanently destroy by finish() or don't keep activity is on.
        super.onDestroy()
        if (!isChangingConfigurations) {
            //when permanently destroy we want to clear the view model.
            (application as MyApplication).clearViewModel()
        }
        Log.d(TAG, "onDestroy: isChangingConfigurations - $isChangingConfigurations")
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, CustomViewModelActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}