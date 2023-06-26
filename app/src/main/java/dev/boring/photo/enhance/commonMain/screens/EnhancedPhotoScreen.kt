package dev.boring.photo.enhance.commonMain.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.boring.photo.enhance.R
import dev.boring.photo.enhance.UserViewModel
import dev.boring.photo.enhance.commonMain.theme.ButtonDescriptionTextStyle
import dev.boring.photo.enhance.commonMain.theme.ButtonTextStyle
import dev.boring.photo.enhance.commonMain.theme.buttonSize

@Composable
fun EnhancedPhotoScreen(userViewModel: UserViewModel, navHostController: NavHostController) {
    val configuration = LocalConfiguration.current
    Column(
        modifier = Modifier
            .padding(
                top = (configuration.screenWidthDp * .01f).dp,
                start = (configuration.screenWidthDp * .01f).dp,
                end = (configuration.screenWidthDp * .01f).dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreviewPhoto(id = R.drawable.man) // TODO: hint for user: touch photo to see how it was
        QualitySwitch()
        DownloadButton()
    }
}

@Composable
private fun QualitySwitch() {

}

@Composable
private fun DownloadButton() {
    val isOpened = remember { mutableStateOf(false) }
    ColoredAlphaButton(
        onClick = { isOpened.value = true },
        modifier = Modifier.buttonSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Download", style = ButtonTextStyle)
            Icon(
                painter = painterResource(id = R.drawable.round_download_24),
                contentDescription = null
            )
        }
    }
    val configuration = LocalConfiguration.current
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
                .clip(shape = RoundedCornerShape(22.dp))
                .background(color = Color.White)
                .padding(
                    all = (configuration.screenWidthDp * .01f).dp,
                )
                .fillMaxHeight()
        ) {
            InnerDownloadButton(
                modifier = Modifier
                    .padding(top = (configuration.screenHeightDp * .01f).dp)
                    .buttonSize()
            )
            RemoveAdsButton(
                modifier = Modifier
                    .padding(vertical = (configuration.screenHeightDp * .01f).dp)
                    .buttonSize()
            )
        }
    }
}

@Composable
private fun InnerDownloadButton(modifier: Modifier) {
    Button(onClick = { }, modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Download", style = ButtonTextStyle)
            Text(text = "Watch an Ad", style = ButtonDescriptionTextStyle)
        }
    }
}

@Composable
@Preview(widthDp = 411, heightDp = 891)
private fun UploadedPhotoScreenPreview() = EnhancedPhotoScreen(
    userViewModel = viewModel(),
    navHostController = rememberNavController()
)
