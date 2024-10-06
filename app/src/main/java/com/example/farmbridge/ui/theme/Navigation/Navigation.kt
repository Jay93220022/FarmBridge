package com.example.farmbridge.ui.theme.Navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmbridge.ui.theme.Navigation.Screen
import com.example.farmbridge.ui.theme.Screens.Dashboard
import com.example.farmbridge.ui.theme.Screens.SelectLanguage
import com.example.farmbridge.ui.theme.Screens.Splash

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.Screens.ForgotPassword
import com.example.farmbridge.ui.theme.Screens.Login
import com.example.farmbridge.ui.theme.Screens.Register
import com.example.farmbridge.ui.theme.ViewModels.AuthViewModel

// Composable Navigation function
@Composable
fun Navigation(authViewModel: AuthViewModel,languageViewModel: LanguageViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val languageViewModel: LanguageViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        // Splash Screen
        composable(route = Screen.Splash.route) {
            Splash(navController = navController)
        }

        composable(route = Screen.SelectLanguage.route) {
            SelectLanguage(

                navController = navController, // Pass NavController
                viewModel = languageViewModel // Pass the ViewModel instance here
            )
        }
        composable(route=Screen.Login.route){
            Login(viewModel = authViewModel,navController)
        }

        composable(route = Screen.Register.route) {
            Register(viewModel = authViewModel, navController = navController)// here
        }

        composable(Screen.ForgotPassword.route){
            ForgotPassword(viewModel =authViewModel,navController=navController)
        }


        composable(route = Screen.Dashboard.route) {
            Dashboard(
                viewModel = languageViewModel
            )
        }


    }
}
