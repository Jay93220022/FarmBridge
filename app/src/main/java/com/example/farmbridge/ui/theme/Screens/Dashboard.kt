package com.example.farmbridge.ui.theme.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.farmbridge.ui.theme.Components.MenuBar
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.Navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    viewModel: LanguageViewModel,
    navController: NavController
) {
    val currentLanguage = viewModel.currentLanguage.collectAsState(initial = "English").value
    var showLanguageDialog by remember { mutableStateOf(false) } // Track dialog visibility

    Scaffold(
        topBar = {
            MenuBar(
                title = "Dashboard",
                onMenuClick = { /* Handle menu click */ },
                onProfileClick = { navController.navigate(Screen.ProfileScreen.route)},
                onSettingsClick = { /* Navigate to settings screen */ },
                onChangeLanguageClick = { showLanguageDialog = true }, // Show language dialog
                navController = navController
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF1F8E9)), // Light green background
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when (currentLanguage) {
                    "Hindi" -> "आपका स्वागत है"
                    "Marathi" -> "आपले स्वागत आहे"
                    else -> "Welcome to FarmBridge"
                },
                color = Color(0xFF2E7D32), // Dark green text color
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Section: Latest Updates
            DashboardCard(
                title = "Latest Updates",
                description = "Stay informed with the latest agricultural news and updates.",
                icon = Icons.Default.ShoppingCart,
                onClick = { navController.navigate(Screen.LatestUpdates.route) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Section: Market Prices
            DashboardCard(
                title = "Market Prices",
                description = "Check the latest market prices for various crops.",
                icon = Icons.Default.ShoppingCart,
                onClick = { navController.navigate(Screen.MarketPrice.route)}
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Section: Weather Updates
            DashboardCard(
                title = "Weather Updates",
                description = "Get real-time weather information for your location.",
                icon = Icons.Default.Person,
                onClick = { navController.navigate(Screen.WeatherScreen.route)}
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Section: Government Schemes
            DashboardCard(
                title = "Government Schemes",
                description = "Explore available schemes and subsidies for farmers.",
                icon = Icons.Default.Person,
                onClick = { /* Navigate to schemes screen */ }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Show the language selection dialog when showLanguageDialog is true
        if (showLanguageDialog) {
            LanguageSelectionDialog(
                viewModel = viewModel,
                onDismiss = { showLanguageDialog = false } // Close dialog on dismiss
            )
        }
    }
}

// Composable for individual dashboard cards
@Composable
fun DashboardCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9)) // Light green card color
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFF388E3C), // Darker green for icons
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF2E7D32)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}


@Composable
fun LanguageSelectionDialog(viewModel: LanguageViewModel, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Select Language",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display language options
                listOf("English", "Hindi", "Marathi").forEach { language ->
                    Button(
                        onClick = {
                            viewModel.setLanguage(language)
                            onDismiss() // Close the dialog after selection
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = language)
                    }
                }
            }
        }
    }
}
