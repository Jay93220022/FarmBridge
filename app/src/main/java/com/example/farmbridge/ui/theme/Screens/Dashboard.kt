// Dashboard.kt
package com.example.farmbridge.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmbridge.R
import com.example.farmbridge.ui.theme.LanguageViewModel

@Composable
fun Dashboard(

    viewModel: LanguageViewModel // Pass the LanguageViewModel to track language changes
) {
    // Observe language changes from the ViewModel
    val currentLanguage = viewModel.currentLanguage.collectAsState(initial = "English").value

    val otpVerificationText = when (currentLanguage) {
        "Hindi" -> "OTP वेरिफिकेशन"
        "Marathi" -> "ओटीपी पडताळणी"
        else -> "Otp verification"
    }

    val welcomeText = when (currentLanguage) {
        "Hindi" -> "App में आपका स्वागत हैे"
        "Marathi" -> "App आपले स्वागत आहे"
        else -> "Welcome to app"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(50.dp)
            .background(Color.Gray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            otpVerificationText,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            welcomeText,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}