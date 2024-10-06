package com.example.farmbridge.ui.theme.Screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.farmbridge.R
import com.example.farmbridge.ui.theme.LanguageViewModel

import com.example.farmbridge.ui.theme.ViewModels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPassword(viewModel: AuthViewModel, navController: NavController,languageViewModel: LanguageViewModel) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val currentLanguage = languageViewModel.currentLanguage.collectAsState(initial = "English").value
    val context = LocalContext.current
    val emailText = when (currentLanguage){
        "Hindi"->"ईमेल"
        "Marathi"->"ईमेल"
        else -> "Email"
    }
    val resetpasswordText = when (currentLanguage){
        "Hindi"->"पासवर्ड रीसेट करें"
        "Marathi"->"पासवर्ड रीसेट करा"
        else -> "Reset Password"
    }
    val peeText = when (currentLanguage){
        "Hindi"->"कृपया ईमेल आईडी दर्ज करें"
        "Marathi"->"कृपया ईमेल आयडी प्रविष्ट करा"
        else -> "Please Enter Email Id"
    }

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier.padding(start = 60.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(emailText, fontWeight = FontWeight.Bold) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    cursorColor = Color(0xFF006838),
                    focusedTextColor = Color(0xFF006838),
                    unfocusedTextColor = Color(0xFF006838),
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
              modifier =   Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (message.isNotEmpty()) {
                Text(text = message, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if(email.isNotEmpty()){
                    viewModel.resetPassword(email, {
                        message = "Reset link sent to $email"
                    }, {
                        message = it
                    })
                }else{
                        Toast.makeText(context, peeText, Toast.LENGTH_SHORT).show()
                    }
                          },
                Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006838),
                    contentColor = Color.White
                )
            ) {
                Text(resetpasswordText)
            }
        }
    }
}