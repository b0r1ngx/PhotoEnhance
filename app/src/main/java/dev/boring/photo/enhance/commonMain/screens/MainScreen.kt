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
import dev.boring.photo.enhance.commonMain.PREVIEW_HEIGHT
import dev.boring.photo.enhance.commonMain.PREVIEW_WIDTH
import dev.boring.photo.enhance.commonMain.theme.ButtonTextStyle
import dev.boring.photo.enhance.commonMain.theme.buttonSize
import kotlin.math.roundToInt

private const val OFFSET_MULTIPLIER = 3
private const val HALF_PART_SCREEN_WEIGHT = 1f
private const val UPLOADED_PHOTO_AS_WINDOW_TOP_PADDING_WEIGHT = .1f
private const val LINE_BETWEEN_IMAGES_EASE_ONE_DIRECTION_DURATION = 4000
private const val TEXT_VERTICAL_PADDING_WEIGHT = .04f

@Composable
fun MainScreen(userViewModel: UserViewModel, navHostController: NavHostController) {
    val configuration = LocalConfiguration.current
    val isUserUploadPhoto = remember { userViewModel.isUserUploadPhoto }
    Box {
        Column {
            CompareImages(modifier = Modifier.weight(HALF_PART_SCREEN_WEIGHT))
            Footer(modifier = Modifier.weight(HALF_PART_SCREEN_WEIGHT)) {
                // TODO: allow (w/ dialog?) user to pick photo from -> Camera / Photos / Files
                isUserUploadPhoto.value = true // call as window
                // navHostController.navigate(Navigation.UploadedPhotoScreen.name) // call as new screen
            }
        }
        AnimatedVisibility(
            visible = isUserUploadPhoto.value,
            modifier = Modifier.padding(
                top = (configuration.screenHeightDp * UPLOADED_PHOTO_AS_WINDOW_TOP_PADDING_WEIGHT).dp
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
            animation = tween(
                durationMillis = LINE_BETWEEN_IMAGES_EASE_ONE_DIRECTION_DURATION,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "DraggableLine.progress"
    )
    val offsetFixedProgress = progress * OFFSET_MULTIPLIER

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.man),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .align(alignment = Alignment.CenterStart)
                .fillMaxSize()
                .drawWithContent {
                    clipRect(right = offsetFixedProgress) {
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
                    clipRect(left = offsetFixedProgress) {
                        this@drawWithContent.drawContent()
                    }
                }
        )
        LineBetweenImages(progress = offsetFixedProgress)
    }
}

// if at testing/updates it start work bad surround it with Box(modifier = Modifier.fillMaxSize()){}
@Composable
private fun LineBetweenImages(
    progress: Float,
    dividerWidth: Dp = 2.dp,
) = Divider(
    modifier = Modifier
        .width(dividerWidth)
        .fillMaxHeight()
        .offset { IntOffset((progress).roundToInt(), 0) },
    color = MaterialTheme.colorScheme.onSecondary
)

@Composable
private fun Footer(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Column(
        modifier = modifier.fillMaxSize(), // padding draws? .drawBehind { continuousMazePattern() }
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RandomText(
            text = "Make your photos\nlook Pro with AI",
            modifier = Modifier.padding(vertical = (screenHeight * TEXT_VERTICAL_PADDING_WEIGHT).dp)
        )
        UploadImageButton(modifier = Modifier.buttonSize(), onClick = onClick)
    }
}

// extra things for button? .background(brush = Brush.linearGradient(colors = rainbowColors))
@Composable
private fun UploadImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = ColoredAlphaButton(onClick = { onClick() }, modifier = modifier) {
    Text(text = "Upload Image", style = ButtonTextStyle)
}


@Composable
@Preview()
@Preview(device = Devices.PIXEL_4)
@Preview(widthDp = PREVIEW_WIDTH, heightDp = PREVIEW_HEIGHT)
private fun MainScreenPreview() = MainScreen(
    userViewModel = viewModel(),
    navHostController = rememberNavController()
)
