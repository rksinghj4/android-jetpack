package com.filedownloader.viewmodelinternals

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.filedownloader.viewmodelinternals.ui.theme.ViewModelInternalsTheme

class MainActivity : ComponentActivity() {
    //private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelInternalsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = MainViewModel()

                    //TestLocalHoisting()
                    //TestViewModel(viewModel)
                    TestViewModelWithLocalCreate()

                }
            }
        }
    }


    @Composable
    fun TestViewModel(viewModel: MainViewModel) {
        val count = viewModel.count
        MainScreen(count = count.intValue) {
            viewModel.increment()
            Log.e(TAG, "count = ${count.intValue}")
        }
    }

    @Composable
    fun TestViewModelWithLocalCreate() {
        Log.d(TAG, "TestViewModelWithLocalCreate called on every change of viewModel.count")
        val viewModel = MainViewModel()//On every recomposition new object is created.
        MainScreen(count = viewModel.count.intValue) {
            viewModel.increment()
            Log.e(TAG, "count = ${viewModel.count.intValue}")
        }
    }


    @Composable
    fun TestLocalHoisting() {//Stateful composable
        val count = remember {
            mutableIntStateOf(0)//MutableIntState
        }
        MainScreen(count = count.intValue) {
            count.intValue++
            Log.e(TAG, "count = ${count.intValue}")
        }
    }
}
