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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
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
    val isUserUploadPhoto = remember { userViewModel.isUserUploadPhoto }
    Box {
        Column { // modifier = Modifier.background(Color.Black)
            CompareImages(modifier = Modifier.weight(1f))
            Footer(
                modifier = Modifier.weight(1f)
            ) { // TODO: allow (w/ dialog?) user where to pick up photo -> Camera / Photos / Files
                isUserUploadPhoto.value = true // navigate(Navigation.UploadedPhotoScreen.name)
            }
        }
        AnimatedVisibility(
            visible = isUserUploadPhoto.value,
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
                isOpened = isUserUploadPhoto
            )
        }
    }
}

@Composable
private fun CompareImages(modifier: Modifier = Modifier) {
    val screenWidthInt = LocalConfiguration.current.screenWidthDp

    val infiniteTransition = rememberInfiniteTransition(label = "DraggableLine")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = screenWidthInt.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "DraggableLine.progress"
    )

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.man),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .align(alignment = Alignment.CenterStart)
                .fillMaxSize()
                .drawWithContent {
                    clipRect(right = 3 * progress) {
                        this@drawWithContent.drawContent()
                    }
                }
        )
        Image(
            painter = painterResource(id = R.drawable.woman),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .align(alignment = Alignment.CenterEnd)
                .fillMaxSize()
                .drawWithContent {
                    clipRect(left = 3 * progress) {
                        this@drawWithContent.drawContent()
                    }
                }
        )
        LineBetweenImages(progress)
    }
}

@Composable
private fun LineBetweenImages(
    progress: Float,
    dividerWidth: Dp = 2.dp,
) = Box(modifier = Modifier.fillMaxSize()) {
    Divider(
        modifier = Modifier
            .width(dividerWidth)
            .fillMaxHeight()
            .offset { IntOffset((3 * progress).roundToInt(), 0) },
        color = MaterialTheme.colorScheme.onSecondary
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
@Preview()
@Preview(device = Devices.PIXEL_4)
@Preview(widthDp = 411, heightDp = 891)
private fun MainScreenPreview() = MainScreen(
    userViewModel = viewModel(),
    navHostController = rememberNavController()
)
