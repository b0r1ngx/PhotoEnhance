package dev.boring.photo.enhance.commonMain.designs

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import dev.boring.photo.enhance.commonMain.theme.Gold

fun DrawScope.continuousMazePattern(color: Color = Gold) {
    repeat(11) {
        drawPath(
            path = mazePattern(Offset(x = it * 100f, y = 0f)),
            color = color,
            style = Stroke(width = 10f)
        )
    }
}

fun mazePattern(offset: Offset = Offset(0f, 0f)) = Path().apply {
    moveTo(offset.x, offset.y + 85f)
    lineTo(offset.x, offset.y)
    lineTo(offset.x + 80f, offset.y)
    lineTo(offset.x + 80f, offset.y + 60f)
    lineTo(offset.x + 40f, offset.y + 60f)
    lineTo(offset.x + 40f, offset.y + 40f)
    lineTo(offset.x + 60f, offset.y + 40f)
    lineTo(offset.x + 60f, offset.y + 20f)
    lineTo(offset.x + 20f, offset.y + 20f)
    lineTo(offset.x + 20f, offset.y + 80f)
    lineTo(offset.x + 100f, offset.y + 80f)
}

fun wrongMazePattern(offset: Offset = Offset(0f, 0f)) = Path().apply {
    moveTo(offset.x, offset.y)
    lineTo(offset.x + 80f, offset.y)
    lineTo(offset.x + 80f, offset.y + 80f)
    lineTo(offset.x + 20f, offset.y + 80f)
    lineTo(offset.x + 20f, offset.y + 40f)
    lineTo(offset.x + 40f, offset.y + 40f)
    lineTo(offset.x + 40f, offset.y + 60f)
    lineTo(offset.x + 60f, offset.y + 60f)
    lineTo(offset.x + 60f, offset.y + 20f)
    lineTo(offset.x, offset.y + 20f)
    lineTo(offset.x, 20f)
    lineTo(offset.x, 100f)
}
