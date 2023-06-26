package dev.boring.photo.enhance.commonMain.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
        QualitySwitch()
        DownloadButton() { }
    }
}

@Composable
private fun QualitySwitch() { // TODO: implement it

}

@Composable
private fun DownloadButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val isOpened = remember { mutableStateOf(false) }
    ColoredAlphaButton(
        onClick = { isOpened.value = true },
        modifier = modifier.buttonSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Download", style = ButtonTextStyle)
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
            Text(text = "Download", style = ButtonTextStyle)
            Text(text = "Watch an Ad", style = ButtonDescriptionTextStyle)
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
