package com.raj.compose.playground.compositionlocal

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
private fun WhyItIsNeeded() {
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

@Composable
private fun TextOne(displayText: String) {//colors: Colors
    Text(
        text = displayText,
        color = LocalColors1.current.primary
    )
}

@Composable
private fun TextTwo(displayText: String) {////Removed colors: Colors
    Text(
        text = displayText,
        color = LocalColors1.current.secondary //Removed colors.secondary
    )
}

@Composable
private fun TextThree(displayText: String) {////Removed colors: Colors
    Text(
        text = displayText,
        color = LocalColors1.current.primary//Removed colors.secondaryLight
    )
}

