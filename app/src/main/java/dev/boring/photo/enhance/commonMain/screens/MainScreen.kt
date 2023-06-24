package dev.boring.photo.enhance.commonMain.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.boring.photo.enhance.R
import dev.boring.photo.enhance.UserViewModel
import kotlin.math.roundToInt

@Composable
fun MainScreen(userViewModel: UserViewModel, navHostController: NavHostController) {
    Column { // modifier = Modifier.background(Color.Black)
        ComparePictures(modifier = Modifier.weight(1f))
        Footer(
            modifier = Modifier.weight(1f),
            onClick = {
                navHostController.navigate(
                    route = Navigation.UploadedPhotoScreen.name
                )
            }
        )
    }
}

@Composable
private fun ComparePictures(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidthInt = configuration.screenWidthDp
    val screenWidth = screenWidthInt.dp
    val progress = remember {
        mutableStateOf((screenWidthInt / 2).toFloat())
    } // screenWidthInt % screenWidthInt

    Box(modifier = modifier) {
        Picture(
            painter = painterResource(id = R.drawable.man),
            width = screenWidth / 2 + progress.value.dp
        )
        Picture(
            painter = painterResource(id = R.drawable.man),
            width = screenWidth / 2 - progress.value.dp
        )
        DraggableLine(progress)
        println("floatValue: ${progress.value}")
    }
}

@Composable
private fun BoxScope.Picture(painter: Painter, width: Dp) = Image(
    painter = painter,
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .align(alignment = Alignment.CenterStart)
        .width(width)
        .fillMaxSize()
)

@Composable
private fun DraggableLine(
    progress: MutableState<Float>,
    dividerWidth: Dp = 2.dp,
) = Box(modifier = Modifier.fillMaxSize()) {
    Divider(
        modifier = Modifier
            .width(dividerWidth)
            .fillMaxHeight()
            .offset { IntOffset((progress.value * 3).roundToInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    progress.value += delta / 3 // center is 0, left -, right +
                }
            ),
        color = Color.White
    )
}

@Composable
private fun Footer(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        RandomText(
            text = "Make your photos\nlook Pro with AI",
            modifier = Modifier.padding(vertical = (configuration.screenHeightDp * .04f).dp)
        )
        UploadImageButton(
            modifier = Modifier
                .size(
                    width = (configuration.screenWidthDp * .8f).dp,
                    height = (configuration.screenHeightDp * .08f).dp
                ),
            onClick = onClick
        )
//        Column(modifier = Modifier.drawBehind { continuousMazePattern() }) {}
    }
}

@Composable
private fun UploadImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
//            .background(
//                brush = Brush.linearGradient(
//                    colors = rainbowColors
//                )
//            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue.copy(alpha = .6f)
        )
    ) {
        Text(
            text = "Upload Image",
            style = TextStyle(
                fontSize = 24.sp
            )
        )
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
private fun MainScreenPreview() = MainScreen(
    userViewModel = viewModel(),
    navHostController = rememberNavController()
)
