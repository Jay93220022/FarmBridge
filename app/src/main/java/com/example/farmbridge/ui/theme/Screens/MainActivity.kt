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
import com.example.farmbridge.ui.theme.Navigation.PreferenceHelper
import com.example.farmbridge.ui.theme.Repository.LanguageRepository
import com.example.farmbridge.ui.theme.Repository.AuthRepository // Import your AuthRepository
import com.example.farmbridge.ui.theme.ViewModels.AuthViewModel // Import your AuthViewModel
import com.example.farmbridge.ui.theme.ViewModels.MarketViewModel
import com.example.farmbridge.ui.theme.ViewModels.WeatherViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Create instances of the repositories
        val languageRepository = LanguageRepository(preferenceHelper = PreferenceHelper(applicationContext))
        val authRepository = AuthRepository()


        setContent {
            FarmBridgeApp(languageRepository,
                authRepository,
                preferenceHelper= PreferenceHelper(applicationContext),
                marketPriceViewModel = MarketViewModel(),
                weatherViewModel = WeatherViewModel()
            )
        }
    }
}

@Composable
fun FarmBridgeApp(languageRepository: LanguageRepository,
                  authRepository: AuthRepository,
                  preferenceHelper: PreferenceHelper,
                  marketPriceViewModel: MarketViewModel,
                  weatherViewModel: WeatherViewModel) {
    val languageViewModel: LanguageViewModel = viewModel(
        factory = LanguageViewModel.Factory(languageRepository,preferenceHelper)
    )

    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(authRepository)
    )

    languageViewModel.setLanguage(languageRepository.getSupportedLanguages().toString())


    // Theme and Navigation
    FarmBridgeTheme {
        Navigation(authViewModel, languageViewModel,marketPriceViewModel,weatherViewModel)
    }
}
