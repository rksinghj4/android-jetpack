package com.raj.compose.playground.compositionlocal

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
private fun WhyItIsNeededSolved() {
    /**
     * Without CompositionLocal we were forced to carry heave weight everywhere, wherever we need it.
     *
     */
    //val colors = Colors()
    Column {
        TextOne(displayText = "TextOne")//removed colors = colors
        DisplayOtherTexts()//colors
    }
}

@Composable
private fun DisplayOtherTexts() {//Removed colors: Colors
    TextTwo(displayText = "TextTwo")//Removed colors = colors
    TextThree(displayText = "TextThree")// Removed  colors = colors
}

@Preview
@Composable
private fun CompositionLocalProviderUpdateWhyItIsNeededSolved() {
    /**
     * Without CompositionLocal we were forced to carry heave weight everywhere, wherever we need it.
     *
     */
    //val colors = Colors()
    Column {
        TextOne(displayText = "TextOne")//removed colors = colors
        CompositionLocalProviderUpdateDisplayOtherTexts()//removed colors = colors
    }
}

@Composable
fun CompositionLocalProviderUpdateDisplayOtherTexts() {
    TextTwo(displayText = "TextTwo")//Removed colors = colors

    val myUpDatedColor = MyColors(
        primary = Color.Black,
        secondary = Color.Magenta,
        secondaryLight = Color.Green
    )
    //CompositionLocalProvider to provide new value to ProvidableCompositionLocal (i.e LocalColors1)
    CompositionLocalProvider(value = LocalColors1 provides myUpDatedColor) {
        TextThree(displayText = "TextThree")// Removed  colors = colors
    }
}

