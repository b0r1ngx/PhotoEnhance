package dev.boring.photo.enhance.commonMain.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.boring.photo.enhance.UserViewModel

@Composable
fun PremiumScreen(userViewModel: UserViewModel, navHostController: NavHostController) {
    Column() {
        Text(text = "Hello im premium screen")
    }
}