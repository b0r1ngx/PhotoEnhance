package dev.boring.photo.enhance.commonMain.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import dev.boring.photo.enhance.commonMain.theme.BigTextStyle
import kotlin.random.Random

@Composable
fun RandomText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = BigTextStyle
) {
    when (Random.nextInt(0, 3)) { // 0, 1, 2 -> create enum ?
        0 -> {
            var (textBefore, textIn) = text.split('\n')
            textBefore += '\n'
            TextRainbow(
                modifier = modifier, textBefore = textBefore, textIn = textIn, style = textStyle
            )
        }

        1 -> TextShadow(modifier = modifier, text = text, style = textStyle)
        2 -> TextWholeGradient(modifier = modifier, text = text, style = textStyle)
    }
}

@Composable
fun RemoveAdsButton() {
    Button(onClick = {  }) {
        Text(text = "Remove Ads")
    }
}