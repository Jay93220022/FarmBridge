package com.example.farmbridge.ui.theme.Screens

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.uitheme.FarmBridgeTheme
import com.example.farmbridge.ui.theme.Navigation.Navigation
import com.example.farmbridge.ui.theme.Repository.LanguageRepository
import com.example.farmbridge.ui.theme.Repository.AuthRepository // Import your AuthRepository
import com.example.farmbridge.ui.theme.ViewModels.AuthViewModel // Import your AuthViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Create instances of the repositories
        val languageRepository = LanguageRepository()
        val authRepository = AuthRepository() // Create an instance of AuthRepository

        setContent {
            FarmBridgeApp(languageRepository, authRepository)
        }
    }
}

@Composable
fun FarmBridgeApp(languageRepository: LanguageRepository, authRepository: AuthRepository) {
    // Initialize the LanguageViewModel using the factory
    val languageViewModel: LanguageViewModel = viewModel(
        factory = LanguageViewModel.Factory(languageRepository)
    )

    // Initialize the AuthViewModel using the factory
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(authRepository) // Assuming you have a Factory in your AuthViewModel
    )

    // Set saved language when the app starts
    languageViewModel.setLanguage(languageRepository.getSupportedLanguages().toString())

    // Theme and Navigation
    FarmBridgeTheme {
        Navigation(authViewModel, languageViewModel) // Pass both ViewModels to Navigation
    }
}
