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
                onProfileClick = { /* Navigate to profile screen */ },
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
                .background(Color.Gray),
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
                color = Color.Black,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add more sections here (e.g., Latest Updates, Market Prices, etc.)
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
                    color = MaterialTheme.colorScheme.primary,
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
                            .padding(vertical = 4.dp)
                    ) {
                        Text(text = language)
                    }
                }
            }
        }
    }
}
