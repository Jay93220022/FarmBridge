package com.example.app.ui.customer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.farmbridge.ui.theme.Components.BottomBarTab
import com.example.farmbridge.ui.theme.Components.BottomNavigationBar

import com.example.farmbridge.ui.theme.Screens.CustomerProductsScreen
import com.example.farmbridge.ui.theme.ViewModels.MarketViewModel

@Composable
fun CustomerDashboard(navController: NavController, marketViewModel: MarketViewModel) {

    val selectedTab = remember { mutableStateOf(BottomBarTab.Products) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedTab = selectedTab.value) { newTab ->
                selectedTab.value = newTab
            }
        }
    ) { paddingValues ->  // Use paddingValues here
        when (selectedTab.value) {
            BottomBarTab.Products -> {
                // Apply paddingValues to the CustomerProductsScreen
                CustomerProductsScreen(marketViewModel, modifier = Modifier.padding(paddingValues))
            }
            BottomBarTab.Profile -> {
                // You can replace this with the actual profile screen or other screens
                CustomerProductsScreen(marketViewModel, modifier = Modifier.padding(paddingValues))
            }
        }
    }
}
