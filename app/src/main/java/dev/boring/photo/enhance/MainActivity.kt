package dev.boring.photo.enhance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dev.boring.photo.enhance.commonMain.navigation.PhotoEnhanceApp
import dev.boring.photo.enhance.commonMain.theme.PhotoEnhanceTheme

// TODO: Use colors only from MaterialTheme.colorScheme
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoEnhanceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PhotoEnhanceApp(userViewModel)
                }
            }
        }
    }
}
