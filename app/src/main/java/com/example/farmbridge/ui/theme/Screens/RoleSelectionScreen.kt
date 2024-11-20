package com.example.farmbridge.ui.theme.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.Navigation.Screen

@Composable
fun RoleSelectionScreen(navController: NavController, languageViewModel: LanguageViewModel) {
    val currentLanguage = languageViewModel.currentLanguage.collectAsState(initial = "English").value

    val titleText = when (currentLanguage) {
        "Hindi" -> "अपना रोल चुनें"
        "Marathi" -> "तुमची भूमिका निवडा"
        else -> "Select Your Role"
    }

    val farmerText = when (currentLanguage) {
        "Hindi" -> "किसान"
        "Marathi" -> "शेतकरी"
        else -> "Farmer"
    }

    val customerText = when (currentLanguage) {
        "Hindi" -> "ग्राहक"
        "Marathi" -> "ग्राहक"
        else -> "Customer"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { navController.navigate(Screen.Login.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(farmerText)
        }

        Button(
            onClick = { navController.navigate(Screen.CustomerLogin.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(customerText)
        }
    }
}
