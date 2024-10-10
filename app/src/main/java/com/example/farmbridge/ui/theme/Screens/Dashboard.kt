// Dashboard.kt
package com.example.farmbridge.ui.theme.Screens
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.farmbridge.R
import com.example.farmbridge.ui.theme.Components.BottomBar
import com.example.farmbridge.ui.theme.Components.BottomNavItem
//import com.example.farmbridge.ui.theme.Components.ImageSlider
import com.example.farmbridge.ui.theme.Components.MenuBar
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.Navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    viewModel: LanguageViewModel,
    navController: NavController
) {
    // Observe language changes from the ViewModel
    val currentLanguage = viewModel.currentLanguage.collectAsState(initial = "English").value

    // Define BottomBar items
    val bottomNavItems = listOf(
        BottomNavItem("Market", painterResource(R.drawable.baseline_show_chart_24), Screen.MarketPrice.route),
        BottomNavItem("Weather", painterResource(R.drawable.baseline_wb_cloudy_24), Screen.WeatherScreen.route), // Weather option added
        BottomNavItem("Profile", painterResource(R.drawable.baseline_person_24), Screen.Splash.route)
    )

    Scaffold(
        topBar = {
            MenuBar(
                title = "Dashboard",
                onMenuClick = { /* Handle menu click */ },
                onProfileClick = { navController.navigate(Screen.Login.route) }, // Navigate to profile
                onSettingsClick = { navController.navigate(Screen.ForgotPassword.route) }, // Navigate to settings
                onChangeLanguageClick = { navController.navigate(Screen.Login.route) },
                navController
            )
        },
        bottomBar = {
            BottomBar(
                items = bottomNavItems,
                onItemClick = { item ->
                    navController.navigate(item.route)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Add padding to avoid overlapping with bars
                .verticalScroll(rememberScrollState())
                .background(Color.Gray),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image Slider displaying the latest information
//            ImageSlider(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .padding(8.dp),
//                images = listOf(
//                    "https://image-url1.com",  // Replace with actual image URLs
//                    "https://image-url2.com",
//                    "https://image-url3.com"
//                )
//            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display current language-based content
            Text(
                text = when (currentLanguage) {
                    "Hindi" -> "आपका स्वागत है"
                    "Marathi" -> "आपले स्वागत आहे"
                    else -> "Welcome to FarmBridge"
                },
                color = Color.Black,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add more sections below (e.g., Latest Updates, Market Prices, etc.)
        }
    }
}
