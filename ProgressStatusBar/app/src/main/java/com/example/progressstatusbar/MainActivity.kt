package com.example.progressstatusbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.example.progressstatusbar.ui.theme.ProgressStatusBarTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.tan
import androidx.compose.ui.geometry.Size


const val PROGRESS_DURATION = 700
const val ONE_EIGHTY = 180f
const val NINETY = 90f
const val PARALLELOGRAM_ANGLE = 70f
const val TWO_SEVENTY = 270f
const val FOUR_EIGHTY = 480
const val ZERO = 0
const val ONE = 1
const val TWO = 2
const val THREE = 3
const val FOUR = 4
const val FIVE = 5

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProgressStatusBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    DeterministicLinearProgressBar()
                }
            }
        }
    }
}

@Composable
internal fun DeterministicLinearProgressBar() {
    Box(
        modifier = Modifier
            .padding(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .wrapContentHeight()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp, bottom = 8.dp),
                text = "Actions taken",
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(FirstArcParallelogram(70f))
                    .height(24.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "1")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(24.dp)
                    .clip(Parallelogram(70f))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "2")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(24.dp)
                    .clip(Parallelogram(70f))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "3")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(24.dp)
                    .clip(Parallelogram(70f))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "4")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(LastArcParallelogram(70f))
                    .height(24.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "5")
            }
        }
    }
}


class FirstArcParallelogram(private val angle: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            Path().apply {
                val radian = (NINETY - angle) * Math.PI / ONE_EIGHTY
                val xOnOpposite = (size.height * tan(radian)).toFloat()
                arcTo(
                    Rect(left = 0f, top = 0f, right = size.height, bottom = size.height),
                    startAngleDegrees = NINETY, sweepAngleDegrees = ONE_EIGHTY, forceMoveTo = true
                )
                lineTo(x = size.width, y = 0f)
                lineTo(x = size.width - xOnOpposite, y = size.height)
                lineTo(x = xOnOpposite, y = size.height)
            }
        )
    }
}


class LastArcParallelogram(private val angle: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            Path().apply {
                val radian = (NINETY - angle) * Math.PI / ONE_EIGHTY
                val xOnOpposite = (size.height * tan(radian)).toFloat()
                moveTo(0f, size.height)
                lineTo(x = xOnOpposite, y = 0f)
                lineTo(x = size.width, y = 0f)
                arcTo(
                    Rect(
                        left = size.width - size.height,
                        top = 0f,
                        right = size.width,
                        bottom = size.height
                    ),
                    startAngleDegrees = TWO_SEVENTY,
                    sweepAngleDegrees = ONE_EIGHTY,
                    forceMoveTo = false
                )
                lineTo(x = xOnOpposite, y = size.height)
            }
        )
    }
}

class Parallelogram(private val angle: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            Path().apply {
                val radian = (NINETY - angle) * Math.PI / ONE_EIGHTY
                val xOnOpposite = (size.height * tan(radian)).toFloat()
                moveTo(0f, size.height)
                lineTo(x = xOnOpposite, y = 0f)
                lineTo(x = size.width, y = 0f)
                lineTo(x = size.width - xOnOpposite, y = size.height)
                lineTo(x = xOnOpposite, y = size.height)
            }
        )
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
internal fun LinearProgressIndicatorPreview() {
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    ProgressStatusBarTheme {
        Greeting("Android")
    }
}