package dev.boring.photo.enhance.commonMain.theme

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

val iconSize = 30.dp
val cornerShape = RoundedCornerShape(size = 22.dp)

fun Modifier.buttonSize(): Modifier = composed {
    val configuration = LocalConfiguration.current
    this.size(
        width = (configuration.screenWidthDp * .8f).dp,
        height = (configuration.screenHeightDp * .08f).dp
    )
}