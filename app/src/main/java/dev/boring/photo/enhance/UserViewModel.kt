package dev.boring.photo.enhance

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    // 3 free usages - ?
    val isUserPremium = mutableStateOf(false)
    val isUserUploadPhoto =  mutableStateOf(false)

}