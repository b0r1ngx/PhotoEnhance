package dev.boring.photo.enhance.commonMain.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.boring.photo.enhance.R
import dev.boring.photo.enhance.commonMain.COLOR_ALPHA
import dev.boring.photo.enhance.commonMain.designs.TextRainbow
import dev.boring.photo.enhance.commonMain.designs.TextShadow
import dev.boring.photo.enhance.commonMain.designs.TextWholeGradient
import dev.boring.photo.enhance.commonMain.theme.BigTextAlignCenterStyle
import dev.boring.photo.enhance.commonMain.theme.ButtonTextStyle
import dev.boring.photo.enhance.commonMain.theme.buttonSize
import dev.boring.photo.enhance.commonMain.theme.cornerShape
import dev.boring.photo.enhance.commonMain.theme.iconSize
import kotlin.random.Random

private const val PREVIEW_PHOTO_WEIGHT = .6f

@Composable
fun RandomText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = BigTextAlignCenterStyle
) {
    when (Random.nextInt(0, 3)) { // 0, 1, 2 -> create enum ?
        0 -> {
            var (textBefore, textIn) = text.split('\n')
            textBefore += '\n'
            TextRainbow(
                textBefore = textBefore, textIn = textIn, modifier = modifier, style = textStyle
            )
        }

        1 -> TextShadow(text = text, modifier = modifier, style = textStyle)
        2 -> TextWholeGradient(text = text, modifier = modifier, style = textStyle)
    }
}

@Composable
fun PreviewPhoto(@DrawableRes id: Int) {
    Image(
        painter = painterResource(id = id),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(shape = cornerShape)
            .height((LocalConfiguration.current.screenHeightDp * PREVIEW_PHOTO_WEIGHT).dp)
    )
}

@Composable
fun RemoveAdsButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    ColoredAlphaButton(onClick = { onClick() }, modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Remove Ads", style = ButtonTextStyle)
            Image(
                painter = painterResource(id = R.drawable.heart_diamond),
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
fun ColoredAlphaButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Blue,
    content: @Composable () -> Unit
) = Button(
    onClick = { onClick() },
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(
        containerColor = containerColor.copy(alpha = COLOR_ALPHA)
    )
) { content() }

@Composable
@Preview
private fun RandomTextPreview() = RandomText(text = "Hello world\nI'm your friend")

@Composable
@Preview
private fun RemoveAdsButtonPreview() = RemoveAdsButton(
    modifier = Modifier.buttonSize()
) { }

@Composable
@Preview
private fun RemoveAdsButtonFromAppPreview() = RemoveAdsButton(
    modifier = Modifier
) { }