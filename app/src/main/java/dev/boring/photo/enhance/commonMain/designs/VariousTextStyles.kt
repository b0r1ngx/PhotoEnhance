package dev.boring.photo.enhance.commonMain.designs

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import dev.boring.photo.enhance.commonMain.theme.bluesColors
import dev.boring.photo.enhance.commonMain.theme.rainbowColors

// TODO: Add more various text styles

@Composable
fun TextShadow(
    text: String,
    modifier: Modifier = Modifier,
    offset: Offset = Offset(5f, 5f),
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        style = style.copy(
            shadow = Shadow(
                color = Color.Blue.copy(alpha = .6f),
                offset = offset,
                blurRadius = 3f
            )
        )
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun TextWholeGradient(
    text: String,
    modifier: Modifier = Modifier,
    colorList: List<Color> = bluesColors,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        style = style.copy(
            brush = Brush.linearGradient(
                colors = colorList
            )
        )
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun TextRainbow(
    textBefore: String,
    textIn: String,
    modifier: Modifier = Modifier,
    textAfter: String = "",
    textBeforeColor: Color = Color.Unspecified,
    textInColors: List<Color> = rainbowColors,
    style: TextStyle = LocalTextStyle.current
) {
//    val brush = Brush.linearGradient(colors = textInColorList)
    Text(
        text = buildAnnotatedString {
//            withStyle(
//                SpanStyle(
//                    brush = brush, alpha = .5f
//                )
//            ) {
//                append(textOut)
//            }
            append(textBefore)
            withStyle(
                SpanStyle(
                    brush = Brush.linearGradient(
                        colors = textInColors
                    )
                )
            ) {
                append(textIn)
            }
            append(textAfter)
        },
        modifier = modifier,
        color = textBeforeColor,
        textAlign = TextAlign.Center,
        style = style
    )
}