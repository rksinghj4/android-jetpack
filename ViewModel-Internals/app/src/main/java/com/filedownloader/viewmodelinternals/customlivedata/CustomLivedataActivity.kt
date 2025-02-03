package com.filedownloader.viewmodelinternals.customlivedata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.filedownloader.viewmodelinternals.ui.theme.ViewModelInternalsTheme

class CustomLivedataActivity : ComponentActivity() {

    private val viewModel by viewModels<TestLiveDataViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ViewModelInternalsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val ColorMapSaver = run {
                        val colorKey = "color"
                        mapSaver(save = {
                            mapOf(
                                colorKey to it.color
                            )
                        },
                            restore = {
                                (it[colorKey] as? Color)?.let { it1 -> MyColor(color = it1) }
                            })
                    }

                    val ColorListSaver = run {
                        listSaver(save = {
                            listOf(
                                it.color
                            )
                        },
                            restore = {
                                (it[0] as? Color)?.let { it1 -> MyColor(color = it1) }
                            })
                    }

                    /*val colorState = rememberSaveable(stateSaver = ColorListSaver) {
                        mutableStateOf(MyColor(Color(0xFFFFFFFF)))
                    }*/

                    val ColorSaver = run {
                        val redKey = "Red"
                        val greenKey = "Green"
                        val blueKey = "Blue"
                        mapSaver(
                            save = {
                                mapOf(
                                    redKey to it.red,
                                    greenKey to it.green,
                                    blueKey to it.blue
                                )
                            },
                            restore = {
                                Color(
                                    red = it[redKey] as Float,
                                    green = it[greenKey] as Float,
                                    blue = it[blueKey] as Float
                                )
                            }
                        )
                    }

                    val singleState = rememberSaveable {
                        mutableStateOf("Raj")
                    }
                    val colorState = rememberSaveable(stateSaver = ColorSaver) {
                        mutableStateOf(Color.Red)
                    }

                    /**
                     * Without lifecycle owner
                     */
//                    viewModel.colorLivedata.observe {
//                        it?.let {
//                            colorState.value = it
//                        }
//                    }
                    /**
                     * With lifecycle owner
                     */
                    viewModel.colorLivedata.observe(this) {
                        it?.let {
                            colorState.value = it
                        }
                    }

                    viewModel.singleLiveEvent.observe(this) {
                        singleState.value = it
                    }

                    Screen(colorState = colorState, singleState.value)
                }
            }
        }
    }

    @Composable
    fun Screen(colorState: State<Color>, singleVale: String) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorState.value)
                    .weight(1f)
                    .clickable {
                        viewModel.updateLiveDataOnMainThread()
                    }

            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Updates : ${colorState.value}"
                    )

                    Text(
                        text = "SingleLiveEvent Updates : $singleVale"
                    )
                }

            }

            Button(modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.updateLiveDataOnMainThread()
                }) {
                Text(text = "LiveData: SetValue on main thread")
            }

            Button(modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.postValueFromBackgroundThread()
                }) {
                Text(text = "LiveData postValue from bg thread")
            }

            Button(modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.updateToSingleLiveEvent()
                }) {
                Text(text = "SingleLiveData: SetValue on main thread")
            }
        }
    }

    data class MyColor(val color: Color)

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, CustomLivedataActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}