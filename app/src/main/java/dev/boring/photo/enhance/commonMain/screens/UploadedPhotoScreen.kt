package dev.boring.photo.enhance.commonMain.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import dev.boring.photo.enhance.commonMain.navigation.Navigation
import dev.boring.photo.enhance.commonMain.theme.ButtonDescriptionTextStyle
import dev.boring.photo.enhance.commonMain.theme.ButtonTextStyle
import dev.boring.photo.enhance.commonMain.theme.buttonSize
import dev.boring.photo.enhance.commonMain.theme.cornerShape
import dev.boring.photo.enhance.commonMain.theme.iconSize

private const val CLOSE_BUTTON_PADDING = 10

@Composable
fun UploadedPhotoScreen(
    userViewModel: UserViewModel,
    navHostController: NavHostController,
    isOpened: MutableState<Boolean>
) {
    val configuration = LocalConfiguration.current
    Box(
        modifier = Modifier
            .clip(shape = cornerShape)
            .background(color = Color.White) // TODO: hard color (MaterialTheme.colorScheme.onSecondary)
            .padding(
                top = (configuration.screenWidthDp * PHOTO_N_BUTTON_PADDING_WEIGHT).dp,
                start = (configuration.screenWidthDp * PHOTO_N_BUTTON_PADDING_WEIGHT).dp,
                end = (configuration.screenWidthDp * PHOTO_N_BUTTON_PADDING_WEIGHT).dp
            )
            .fillMaxHeight()
    ) {
        PreviewPhoto(id = R.drawable.woman)
        CloseButton(isOpened = isOpened, modifier = Modifier.align(Alignment.TopEnd))
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            EnhancePhotoButton(modifier = Modifier.buttonSize()) {
                navHostController.navigate(Navigation.EnhancedPhotoScreen.name)
            }
            RemoveAdsButton(
                modifier = Modifier
                    .padding(vertical = (configuration.screenHeightDp * PHOTO_N_BUTTON_PADDING_WEIGHT).dp)
                    .buttonSize()
            ) { }
        }
    }
}

@Composable
private fun CloseButton(
    isOpened: MutableState<Boolean>,
    modifier: Modifier = Modifier
) = Icon(
    painter = painterResource(id = R.drawable.round_cancel_24),
    contentDescription = null,
    modifier = modifier
        .padding(CLOSE_BUTTON_PADDING.dp)
        .size(iconSize)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { isOpened.value = false }
)

@Composable
private fun EnhancePhotoButton(modifier: Modifier, onClick: () -> Unit) =
    ColoredAlphaButton(onClick = { onClick() }, modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(id = R.string.uploaded_enhance_image), style = ButtonTextStyle)
            Text(text = stringResource(id = R.string.watch_an_ad), style = ButtonDescriptionTextStyle)
        }
    }

@Composable
@Preview
@Preview(device = Devices.PIXEL_4)
@Preview(widthDp = PREVIEW_WIDTH, heightDp = PREVIEW_HEIGHT)
private fun UploadedPhotoScreenPreview() = UploadedPhotoScreen(
    userViewModel = viewModel(),
    navHostController = rememberNavController(),
    isOpened = remember { mutableStateOf(true) }
)
