// SelectLanguage.kt
package com.example.farmbridge.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.Navigation.Screen

@Composable
fun SelectLanguage(navController: NavController, viewModel: LanguageViewModel) {
    // Retrieve the supported languages from the repository
    val supportedLanguages = viewModel.getSupportedLanguages()

    // Arrange buttons in a Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select Language",
            fontSize =    24.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
        )
        Spacer(Modifier.height(30.dp))
        for (language in supportedLanguages) {


            Button(
                onClick = {
                    viewModel.setLanguage(language) // Set the selected language
                    navController.navigate(Screen.Login.route) // Navigate to the Dashboard
                },
               colors = ButtonDefaults.buttonColors(
                   contentColor = Color.Black,
                   containerColor = Color.Green
               ),
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
                                   .fillMaxWidth(0.8f)
                                   .height(80.dp)
            ) {
                Text(text = language)
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}
