package dev.boring.photo.enhance.common.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.boring.photo.enhance.common.RemoveAdsButton

@Composable
fun UploadedPhotoScreen() {
    Column {
        CloseButton()
        PreviewPhoto()
        EnhancePhotoButton()
        RemoveAdsButton()
    }
}

@Composable
fun CloseButton() {
    TODO("Not yet implemented")
}


@Composable
fun PreviewPhoto() {
    TODO("Not yet implemented")
}

@Composable
fun EnhancePhotoButton() {
    TODO("Not yet implemented")
}

@Preview
@Composable
fun UploadedPhotoScreenPreview() = UploadedPhotoScreen()
