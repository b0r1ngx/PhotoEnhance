package dev.boring.photo.enhance.common.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.boring.photo.enhance.R
import dev.boring.photo.enhance.common.TextRainbow
import dev.boring.photo.enhance.common.data.DragAnchors
import kotlin.math.roundToInt

@Composable
fun MainScreen() {
    Column(Modifier.background(Color.Black)) {
        ComparePhoto(modifier = Modifier.weight(1f))
        Footer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextRainbow(
            textBefore = "Make your photos\nlook Pro ",
            outsideTextColor = Color.White,
            textIn = "with AI"
        )
        UploadImageButton()
    }
}

@Composable
fun ComparePhoto(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val progress by remember { mutableFloatStateOf(.5f) }

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.man),
            contentDescription = null,
//            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize().width(screenWidth * progress)
        ) // why is that coming to back-background
        Image(
            painter = painterResource(id = R.drawable.woman),
            contentDescription = null,
//            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize().width(screenWidth * (1 - progress))
        ) // this going to top
//        HorizontalDraggableSample() // this goes middle :| crazZY
        Draggable()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalDraggableSample(
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val contentSize = 2.dp
    val contentSizePx = with(density) { contentSize.toPx() }
    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Half,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { contentSizePx },
            animationSpec = tween(),
        )
    }

    Box(
        modifier
            .fillMaxSize()
            .onSizeChanged { layoutSize ->
                val dragEndPoint = layoutSize.width - contentSizePx
                state.updateAnchors(
                    DraggableAnchors {
                        DragAnchors
                            .values()
                            .forEach { anchor ->
                                anchor at dragEndPoint * anchor.fraction
                            }
                    }
                )
            }
    ) {
        Divider(
            modifier = Modifier
                .width(contentSize)
                .fillMaxHeight()
                .offset {
                    IntOffset(
                        x = state
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                .anchoredDraggable(state, Orientation.Horizontal),
            color = Color.White
        )
    }
}

@Composable
fun Draggable() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Horizontally Draggable Modifier
        var offsetX by remember { mutableStateOf(0f) }
        Text(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offsetX += delta
                    }
                ),
            text = "I move Horizontally!", fontSize = 20.sp
        )

        // Adding a Space of 100dp height
        Spacer(modifier = Modifier.height(100.dp))

        // Vertically Draggable Modifier
        var offsetY by remember { mutableStateOf(0f) }
        Text(
            modifier = Modifier
                .offset { IntOffset(0, offsetY.roundToInt()) }
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        offsetY += delta
                    }
                ),
            text = "I move Vertically!", fontSize = 20.sp
        )

    }
}

@Composable
fun UploadImageButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { },
        modifier = modifier
    ) {
        Text(text = "Upload Image")
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}