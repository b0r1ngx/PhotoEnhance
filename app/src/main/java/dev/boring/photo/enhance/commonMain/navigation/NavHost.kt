package dev.boring.photo.enhance.commonMain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.boring.photo.enhance.UserViewModel
import dev.boring.photo.enhance.commonMain.screens.EnhancedPhotoScreen
import dev.boring.photo.enhance.commonMain.screens.MainScreen
import dev.boring.photo.enhance.commonMain.screens.PremiumScreen

@Composable
fun PhotoEnhanceApp(userViewModel: UserViewModel) {
    val navHostController = rememberNavController()
    AppNavHost(userViewModel, navHostController)
}

@Composable
fun AppNavHost(userViewModel: UserViewModel, navHostController: NavHostController) =
    NavHost(navController = navHostController, startDestination = Navigation.MainScreen.name) { // MainScreen
        composable(route = Navigation.MainScreen.name) {
            MainScreen(userViewModel, navHostController)
        }

        composable(route = Navigation.UploadedPhotoScreen.name) {
//            UploadedPhotoScreen(userViewModel, navHostController)
        }

        composable(route = Navigation.EnhancedPhotoScreen.name) {
            EnhancedPhotoScreen(userViewModel, navHostController)
        }

        composable(route = Navigation.PremiumScreen.name) {
            PremiumScreen(userViewModel, navHostController)
        }
    }
