package dev.boring.photo.enhance.commonMain.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.boring.photo.enhance.UserViewModel

@Composable
fun UploadedPhotoScreen(userViewModel: UserViewModel, navHostController: NavHostController) {
    Column {
        CloseButton()
        PreviewPhoto()
        EnhancePhotoButton()
        RemoveAdsButton()
    }
}

@Composable
fun CloseButton() {

}


@Composable
fun PreviewPhoto() {

}

@Composable
fun EnhancePhotoButton() {

}

@Preview
@Composable
fun UploadedPhotoScreenPreview() =
    UploadedPhotoScreen(
        userViewModel = viewModel(),
        navHostController = rememberNavController()
    )
