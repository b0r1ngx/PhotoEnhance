package dev.boring.photo.enhance.commonMain.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.boring.photo.enhance.R
import dev.boring.photo.enhance.UserViewModel
import dev.boring.photo.enhance.commonMain.theme.ButtonTextStyle
import dev.boring.photo.enhance.commonMain.theme.buttonSize
import kotlin.math.roundToInt

@Composable
fun MainScreen(userViewModel: UserViewModel, navHostController: NavHostController) {
    val configuration = LocalConfiguration.current
    val isOpened = remember { mutableStateOf(false) }
    Box {
        Column { // modifier = Modifier.background(Color.Black)
            ComparePictures(modifier = Modifier.weight(1f))
            Footer(
                modifier = Modifier.weight(1f)
            ) { // TODO: allow (w/ dialog?) user where to pick up photo -> Camera / Photos / Files
                isOpened.value = true // navigate(Navigation.UploadedPhotoScreen.name)
            }
        }
        AnimatedVisibility(
            visible = isOpened.value,
            modifier = Modifier.padding(
                top = (configuration.screenHeightDp * .1f).dp
            ),
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessVeryLow
                ),
                initialOffsetY = { it }
            ),
            exit = slideOutVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessVeryLow
                ),
                targetOffsetY = { it }
            )
        ) {
            UploadedPhotoScreen(
                userViewModel = userViewModel,
                navHostController = navHostController,
                isOpened = isOpened
            )
        }
    }
}

@Composable
private fun ComparePictures(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidthInt = configuration.screenWidthDp
    val screenWidthFloat = configuration.screenWidthDp.toFloat()
    val screenWidth = screenWidthInt.dp

    val infiniteTransition = rememberInfiniteTransition(label = "DraggableLine")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = screenWidthFloat,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "DraggableLine.progress"
    )

    Box(modifier = modifier) {
        Picture(
            modifier = Modifier
                .align(alignment = Alignment.CenterStart),
            painter = painterResource(id = R.drawable.man),
            width = progress.dp
        )
        Picture(
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd),
            painter = painterResource(id = R.drawable.woman),
            width = progress.dp - screenWidth
        )
        DraggableLine(progress)
        println("floatValue: ${progress}")
    }
}

@Composable
private fun Picture(
    modifier: Modifier = Modifier,
    painter: Painter,
    width: Dp
) = Image(
    painter = painter,
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = modifier
        .width(width)
        .fillMaxSize()
)

@Composable
private fun DraggableLine(
    progress: Float,
    dividerWidth: Dp = 2.dp,
) = Box(modifier = Modifier.fillMaxSize()) {
    Divider(
        modifier = Modifier
            .width(dividerWidth)
            .fillMaxHeight()
            .offset { IntOffset((progress * 3).roundToInt(), 0) },
        color = Color.White
    )
}

@Composable
private fun Footer(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    Column(
        modifier = modifier
            .fillMaxSize(),
//            .drawBehind { continuousMazePattern() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RandomText(
            text = "Make your photos\nlook Pro with AI",
            modifier = Modifier.padding(vertical = (configuration.screenHeightDp * .04f).dp)
        )
        UploadImageButton(
            modifier = Modifier
                .buttonSize(),
            onClick = onClick
        )
    }
}

@Composable
private fun UploadImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ColoredAlphaButton(
        onClick = { onClick() },
        modifier = modifier
//            .background(
//                brush = Brush.linearGradient(
//                    colors = rainbowColors
//                )
//            ),
    ) {
        Text(text = "Upload Image", style = ButtonTextStyle)
    }
}

@Composable
@Preview(device = Devices.PIXEL_4)
private fun MainScreenPreview() = MainScreen(
    userViewModel = viewModel(),
    navHostController = rememberNavController()
)
