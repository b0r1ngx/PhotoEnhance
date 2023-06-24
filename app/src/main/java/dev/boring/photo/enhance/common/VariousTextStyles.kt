package dev.boring.photo.enhance.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import dev.boring.photo.enhance.common.theme.GoogleLightBlue
import dev.boring.photo.enhance.common.theme.Orange
import dev.boring.photo.enhance.common.theme.Purple

@Composable
fun TextShadow(
    text: String,
    textStyle: TextStyle = TextStyle(),
    offset: Offset = Offset(5.0f, 10.0f)
) {
    Text(
        text = text,
        style = textStyle.copy(
            fontSize = 24.sp,
            shadow = Shadow(
                color = Color.Blue,
                offset = offset,
                blurRadius = 3f
            )
        )
    )
}

@Composable
fun TextBrush(
    text: String,
    colorList: List<Color> = listOf(
        Color.Cyan, GoogleLightBlue, Purple
    )
) {
    Text(
        text = text,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = colorList
            )
        )
    )
}

@Composable
fun TextRainbow(
    modifier: Modifier = Modifier,
    textBefore: String,
    outsideTextColor: Color,
    textIn: String,
    textInColorList: List<Color> = listOf(
        Color.Red, Orange, Color.Yellow,
        Color.Green, Color.Cyan, Color.Blue, Purple
    ),
    textAfter: String = ""
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
                        colors = textInColorList
                    )
                )
            ) {
                append(textIn)
            }
//            append("textAfter")
        },
        modifier = modifier,
        color = outsideTextColor,
        textAlign = TextAlign.Center
    )
}