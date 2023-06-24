package dev.boring.photo.enhance.common.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import dev.boring.photo.enhance.common.RemoveAdsButton

@Composable
fun EnhancedPhotoScreen() {
    Column {
        DownloadButton()

    }
}

@Composable
fun DownloadButton() {
    Button(onClick = {  }) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "")
//            Image(
//                painter =  painterResource(id = )
//            )
        }
    }
    Column {
        innerDownloadButton()
        RemoveAdsButton()
    }
}

fun innerDownloadButton() {
    TODO("Not yet implemented")
}
