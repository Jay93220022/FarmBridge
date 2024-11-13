package com.example.farmbridge.ui.theme.Components

import MarketPrice
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.farmbridge.R
import com.example.farmbridge.ui.Screens.AddCropScreen
import com.example.farmbridge.ui.theme.DataClasses.NavItem
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.Screens.Dashboard
import com.example.farmbridge.ui.theme.Screens.Login
import com.example.farmbridge.ui.theme.Screens.WeatherScreen
import com.example.farmbridge.ui.theme.ViewModels.MarketViewModel
import com.example.farmbridge.ui.theme.ViewModels.WeatherViewModel
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    languageViewModel: LanguageViewModel,
    navController: NavController,
    marketViewModel: MarketViewModel,
    context: android.content.Context
) {
    // Fetch current language
    val currentLanguage = languageViewModel.currentLanguage.collectAsState(initial = "English").value

    val navItemList = listOf(
        NavItem(
            when (currentLanguage) {
                "Hindi" -> "मुख्य पृष्ठ"
                "Marathi" -> "मुख्य पृष्ठ"
                else -> "Home"
            },
            Icons.Default.Home
        ),
        NavItem(
            when (currentLanguage) {
                "Hindi" -> "बाजार"
                "Marathi" -> "बाजार"
                else -> "Market"
            },
            ImageVector.vectorResource(id = R.drawable.baseline_show_chart_24)
        ),
        NavItem(
            when (currentLanguage) {
                "Hindi" -> "मौसम"
                "Marathi" -> "हवामान"
                else -> "Weather"
            },
            ImageVector.vectorResource(id = R.drawable.baseline_wb_cloudy_24)
        ),
        NavItem(
            when (currentLanguage) {
                "Hindi" -> "फसल देखभाल"
                "Marathi" -> "पिकांची काळजी"
                else -> "Crop Care"
            },
            ImageVector.vectorResource(id = R.drawable.baseline_search_24)
        )
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedIndex == 0,
                        onClick = { selectedIndex = 0 },
                        icon = { Icon(navItemList[0].icon, contentDescription = null) },
                        label = { Text(text = navItemList[0].label) }
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 1,
                        onClick = { selectedIndex = 1 },
                        icon = { Icon(navItemList[1].icon, contentDescription = null) },
                        label = { Text(text = navItemList[1].label) }
                    )

                    Spacer(modifier = Modifier.width(56.dp))

                    NavigationBarItem(
                        selected = selectedIndex == 2,
                        onClick = { selectedIndex = 2 },
                        icon = { Icon(navItemList[2].icon, contentDescription = null) },
                        label = { Text(text = navItemList[2].label) }
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 3,
                        onClick = { selectedIndex = 3 },
                        icon = { Icon(navItemList[3].icon, contentDescription = null) },
                        label = { Text(text = navItemList[3].label) }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    FloatingActionButton(
                        onClick = { selectedIndex = 5 },
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White,
                        elevation = FloatingActionButtonDefaults.elevation(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = when (currentLanguage) {
                                "Hindi" -> "फसल जोड़ें"
                                "Marathi" -> "पिक जोडा"
                                else -> "Add Crop"
                            },
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex,
            languageViewModel,
            navController,
            weatherViewModel,
            context,
            marketViewModel
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    languageViewModel: LanguageViewModel,
    navController: NavController,
    weatherViewModel: WeatherViewModel,
    context: android.content.Context,
    marketViewModel: MarketViewModel
) {
    when (selectedIndex) {
        0 -> Dashboard(viewModel = languageViewModel, navController = navController)
        1 -> MarketPrice(marketViewModel,languageViewModel)
        2 -> WeatherScreen(
            weatherViewModel = weatherViewModel,
            languageViewModel = languageViewModel,
            modifier = modifier
        )
        5 -> AddCropScreen(marketViewModel, context,languageViewModel)
        3 -> WeatherScreen(
            weatherViewModel = weatherViewModel,
            languageViewModel = languageViewModel,
            modifier = modifier
        )
    }
}
