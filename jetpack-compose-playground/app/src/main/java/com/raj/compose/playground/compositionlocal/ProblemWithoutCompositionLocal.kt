package com.raj.compose.playground.compositionlocal

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

data class Colors(
    val primary: Color = Color.Blue,
    val secondary: Color = Color.Gray,
    val secondaryLight: Color = Color.LightGray
)

@Preview
@Composable
private fun WhyItIsNeeded() {
    /**
     * Without CompositionLocal we are forced to carry heave weight everywhere, wherever we need it.
     */
    val colors = Colors()
    Column {
        TextOne(displayText = "TextOne", colors = colors)
        DisplayOtherTexts(colors)
    }
}

@Composable
private fun DisplayOtherTexts(colors: Colors) {
    TextTwo(displayText = "TextTwo", colors = colors)
    TextThree(displayText = "TextThree", colors = colors)
}

@Composable
private fun TextOne(displayText: String, colors: Colors) {
    Text(
        text = displayText,
        color = colors.primary
    )
}

@Composable
private fun TextTwo(displayText: String, colors: Colors) {
    Text(
        text = displayText,
        color = colors.secondary
    )
}

@Composable
private fun TextThree(displayText: String, colors: Colors) {
    Text(
        text = displayText,
        color = colors.secondaryLight
    )
}
