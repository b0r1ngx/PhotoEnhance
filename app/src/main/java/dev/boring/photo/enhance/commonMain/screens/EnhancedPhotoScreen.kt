package dev.boring.photo.enhance.commonMain.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.boring.photo.enhance.R
import dev.boring.photo.enhance.UserViewModel
import dev.boring.photo.enhance.commonMain.PHOTO_N_BUTTON_PADDING_WEIGHT
import dev.boring.photo.enhance.commonMain.PREVIEW_HEIGHT
import dev.boring.photo.enhance.commonMain.PREVIEW_WIDTH
import dev.boring.photo.enhance.commonMain.theme.ButtonDescriptionTextStyle
import dev.boring.photo.enhance.commonMain.theme.ButtonTextStyle
import dev.boring.photo.enhance.commonMain.theme.PickedQuality
import dev.boring.photo.enhance.commonMain.theme.buttonSize
import dev.boring.photo.enhance.commonMain.theme.cornerShape
import dev.boring.photo.enhance.commonMain.theme.iconSize

@Composable
fun EnhancedPhotoScreen(userViewModel: UserViewModel, navHostController: NavHostController) {
    val configuration = LocalConfiguration.current
    Column(
        modifier = Modifier
            .padding(
                top = (configuration.screenWidthDp * PHOTO_N_BUTTON_PADDING_WEIGHT).dp,
                start = (configuration.screenWidthDp * PHOTO_N_BUTTON_PADDING_WEIGHT).dp,
                end = (configuration.screenWidthDp * PHOTO_N_BUTTON_PADDING_WEIGHT).dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreviewPhoto(id = R.drawable.man) // TODO: hint for user: touch photo to see how it was
        QualitySwitch(
            modifier = Modifier
                .padding(vertical = (configuration.screenHeightDp * 2 * PHOTO_N_BUTTON_PADDING_WEIGHT).dp),
            quality = remember { if (userViewModel.isUserPremium.value) Quality.HQ else Quality.Base }
        )
        DownloadButton() { }
    }
}

enum class Quality {
    Base, HQ
}

@Composable
private fun QualitySwitch(modifier: Modifier = Modifier, quality: Quality) { // TODO: implement it
//    val transition = updateTransition(
//        quality,
//        label = "Tab indicator"
//    )
//    val indicatorLeft by transition.animateDp(
//        transitionSpec = {
//            if (Quality.Base isTransitioningTo Quality.HQ) {
//                // Indicator moves to the right.
//                // Low stiffness spring for the left edge so it moves slower than the right edge.
//                spring(stiffness = Spring.StiffnessVeryLow)
//            } else {
//                // Indicator moves to the left.
//                // Medium stiffness spring for the left edge so it moves faster than the right edge.
//                spring(stiffness = Spring.StiffnessMedium)
//            }
//        },
//        label = "Indicator left"
//    ) { page ->
//        tabPositions[page.ordinal].left
//    }
//
//    val indicatorRight by transition.animateDp(
//        transitionSpec = {
//            if (Quality.Base isTransitioningTo Quality.HQ) {
//                // Indicator moves to the right
//                // Medium stiffness spring for the right edge so it moves faster than the left edge.
//                spring(stiffness = Spring.StiffnessMedium)
//            } else {
//                // Indicator moves to the left.
//                // Low stiffness spring for the right edge so it moves slower than the left edge.
//                spring(stiffness = Spring.StiffnessVeryLow)
//            }
//        },
//        label = "Indicator right"
//    ) { page ->
//        tabPositions[page.ordinal].right
//    }

    Row(
        modifier
//            .offset(x = indicatorLeft)
            .background(color = Color.Black, shape = cornerShape)
            .height(22.dp)
            .border(
                width = 1.dp,
                color = PickedQuality,
                shape = cornerShape
            )
    ) {
        Text(
            text = "Base", modifier
                .background(color = PickedQuality, shape = cornerShape)
                .padding(
                    horizontal = 14.dp,
                    vertical = 7.dp
                ),
            style = ButtonDescriptionTextStyle.copy(color = Color.White)
        )
        Text(
            text = "HD", modifier
                .background(color = Color.Black, shape = cornerShape)
                .padding(
                    horizontal = 14.dp,
                    vertical = 7.dp
                ),
            style = ButtonDescriptionTextStyle.copy(color = Color.White)
        )
    }
}

@Composable
private fun DownloadButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val isOpened = remember { mutableStateOf(false) }
    ColoredAlphaButton(
        onClick = { isOpened.value = true },
        modifier = modifier.buttonSize(),
        containerColor = Color.Black
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.enhanced_download), style = ButtonTextStyle)
            Icon(
                painter = painterResource(id = R.drawable.round_download_24),
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )
        }
    }
    AnimatedVisibility(
        visible = isOpened.value,
        enter = slideInVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow
            ),
            initialOffsetY = { it / 4 }
        ),
        exit = slideOutVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow
            ),
            targetOffsetY = { it / 4 }
        )
    ) {
        Column(
            modifier = Modifier
                .clip(shape = cornerShape)
                .background(color = Color.White) // TODO: hard color (MaterialTheme.colorScheme.onSecondary)
                .padding(all = (configuration.screenWidthDp * PHOTO_N_BUTTON_PADDING_WEIGHT).dp)
                .fillMaxHeight()
        ) {
            InnerDownloadButton(
                modifier = Modifier
                    .padding(top = (configuration.screenHeightDp * PHOTO_N_BUTTON_PADDING_WEIGHT).dp)
                    .buttonSize()
            ) { }
            RemoveAdsButton(
                modifier = Modifier
                    .padding(vertical = (configuration.screenHeightDp * PHOTO_N_BUTTON_PADDING_WEIGHT).dp)
                    .buttonSize()
            ) { }
        }
    }
}

@Composable
private fun InnerDownloadButton(modifier: Modifier, onClick: () -> Unit) {
    Button(onClick = { onClick() }, modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(id = R.string.enhanced_download), style = ButtonTextStyle)
            Text(
                text = stringResource(id = R.string.watch_an_ad),
                style = ButtonDescriptionTextStyle
            )
        }
    }
}

@Composable
@Preview
@Preview(device = Devices.PIXEL_4)
@Preview(widthDp = PREVIEW_WIDTH, heightDp = PREVIEW_HEIGHT)
private fun UploadedPhotoScreenPreview() = EnhancedPhotoScreen(
    userViewModel = viewModel(),
    navHostController = rememberNavController()
)
