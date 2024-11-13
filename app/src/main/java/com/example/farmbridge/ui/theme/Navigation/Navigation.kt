package com.example.farmbridge.ui.theme.Navigation


import MarketPrice



import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmbridge.ui.theme.Navigation.Screen

import com.example.farmbridge.ui.theme.Screens.SelectLanguage
import com.example.farmbridge.ui.theme.Screens.Splash

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmbridge.ui.Screens.AddCropScreen
import com.example.farmbridge.ui.theme.Components.MainScreen
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.Screens.Dashboard
import com.example.farmbridge.ui.theme.Screens.ForgotPassword
import com.example.farmbridge.ui.theme.Screens.LatestUpdatesScreen
import com.example.farmbridge.ui.theme.Screens.Login

import com.example.farmbridge.ui.theme.Screens.Register
import com.example.farmbridge.ui.theme.Screens.WeatherScreen
import com.example.farmbridge.ui.theme.ViewModels.AuthViewModel
import com.example.farmbridge.ui.theme.ViewModels.MarketViewModel
import com.example.farmbridge.ui.theme.ViewModels.WeatherViewModel

// Composable Navigation function
@Composable
fun Navigation(authViewModel: AuthViewModel,
               languageViewModel: LanguageViewModel,
               marketPriceViewModel: MarketViewModel,
               weatherViewModel: WeatherViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current


    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        // Splash Screen
        composable(route = Screen.Splash.route) {
            Splash(navController = navController)
        }

        composable(route = Screen.SelectLanguage.route) {
            SelectLanguage(

                navController = navController,
                viewModel = languageViewModel
            )
        }
        composable(route=Screen.Login.route){
            Login(authViewModel,navController,languageViewModel,)
        }

        composable(route = Screen.Register.route) {
            Register(viewModel = authViewModel, navController = navController,languageViewModel)// here
        }

        composable(Screen.ForgotPassword.route){
            ForgotPassword(viewModel =authViewModel,navController=navController,languageViewModel)
        }


        composable(route = Screen.Dashboard.route) {
            Dashboard(
                viewModel = languageViewModel,
                navController
            )
        }
        composable(route =Screen.MarketPrice.route){
            MarketPrice(marketPriceViewModel,languageViewModel)
        }
composable(Screen.WeatherScreen.route){
    WeatherScreen(weatherViewModel,languageViewModel, modifier = Modifier)
}
        composable(Screen.MainScreen.route){
            MainScreen(modifier = Modifier,weatherViewModel,languageViewModel,navController,marketPriceViewModel, context )
        }
        composable(Screen.AddCropnScreen.route){
            AddCropScreen(marketPriceViewModel ,context, languageViewModel )
        }

        composable(Screen.LatestUpdates.route) {
            LatestUpdatesScreen(navController = navController)
        }
    }
}
