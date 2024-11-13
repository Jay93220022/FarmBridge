package com.example.farmbridge.ui.theme.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.farmbridge.R
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.Navigation.Screen

@SuppressLint("SuspiciousIndentation")
@Composable
fun SelectLanguage(navController: NavController, viewModel: LanguageViewModel) {
    val currentLanguage = viewModel.currentLanguage.collectAsState().value
    val supportedLanguages = viewModel.getSupportedLanguages()

    // If a language is already selected, navigate to the next screen (e.g., Dashboard)
//    LaunchedEffect(currentLanguage) {
//        if (currentLanguage != "English") {  // Adjust this if needed for the default language
//            navController.navigate(Screen.MainScreen.route) {
//               // popUpTo(Screen.SelectLanguage.route) { inclusive = true }
//            }
//        }
//    }

    // Language selection screen
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier.padding(start = 60.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .offset(y = (-50).dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Select Language",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(30.dp))

            for (language in supportedLanguages) {
                Button(
                    onClick = {
                        viewModel.setLanguage(language)
                        navController.navigate(Screen.MainScreen.route) {
                         //   popUpTo(Screen.SelectLanguage.route) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color(0xFF006838)
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = language, color = Color.White)
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}
