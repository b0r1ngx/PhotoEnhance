package dev.boring.photo.enhance

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    // 3 free usages - ?
    val isUserUploadPhoto =  mutableStateOf(false)

}