// RegisterScreen.kt
package com.example.farmbridge.ui.theme.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.farmbridge.R
import com.example.farmbridge.ui.theme.Navigation.Screen
import com.example.farmbridge.ui.theme.ViewModels.AuthViewModel


import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.farmbridge.ui.theme.LanguageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(viewModel: AuthViewModel, navController: NavController,languageViewModel: LanguageViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordVisible1 by remember { mutableStateOf(false) }
    val currentLanguage = languageViewModel.currentLanguage.collectAsState(initial = "English").value
    val context = LocalContext.current
    val emailText = when (currentLanguage){
        "Hindi"->"ईमेल"
        "Marathi"->"ईमेल"
        else -> "Email"
    }
    val passwordText = when (currentLanguage){
        "Hindi"->"पासवर्ड"
        "Marathi"->"पासवर्ड"
        else -> "Password"
    }
    val confirmPasswordText = when (currentLanguage){
        "Hindi"->"पासवर्ड की पुष्टि करें"
        "Marathi"->"पासवर्ड ची पुष्टी करा"
        else -> " Comfirm Password"
    }
    val pedText = when (currentLanguage){
        "Hindi"->"कृपया पासवर्ड की पुष्टि करेंं"
        "Marathi"->"कृपया पासवर्ड ची पुष्टी करा"
        else -> "Please Confirm the Password"}

            val peeText = when (currentLanguage){
            "Hindi"->"कृपया ईमेल आईडी दर्ज करें"
            "Marathi"->"कृपया ईमेल आयडी प्रविष्ट करा"
            else -> "Please Enter Email Id"
        }
            val pepText = when (currentLanguage){
            "Hindi"->"कृपया पासवर्ड दर्ज करें"
            "Marathi"->"कृपया पासवर्ड प्रविष्ट करा"
            else -> "Please Enter Password"
        }
    val pdmText = when (currentLanguage){
        "Hindi"->"पासवर्ड मेल नहीं खाते"
        "Marathi"->"पासवर्ड जुळत नाहीत"
        else -> "Passwords do not match"
    }
    val registerText = when (currentLanguage){
        "Hindi"->"पंजीकृत करें"
        "Marathi"->"नोंदणी करणे"
        else -> "Register"
    }
    Box(Modifier.fillMaxSize()){
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
                label = { Text(emailText, fontWeight = FontWeight.Bold )},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedTextColor = Color(0xFF006838),
                    unfocusedTextColor = Color(0xFF006838),
                    cursorColor = Color(0xFF006838)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(passwordText, fontWeight = FontWeight.Bold) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                trailingIcon = {
                    val iconResId = if (passwordVisible) {
                        R.drawable.visible_image
                    } else {
                        R.drawable.invisible_image
                    }

                    Image(
                        painter = painterResource(id = iconResId),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedTextColor = Color(0xFF006838),
                    unfocusedTextColor = Color(0xFF006838),
                    cursorColor = Color(0xFF006838)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(confirmPasswordText, fontWeight = FontWeight.Bold) },
                visualTransformation = if (passwordVisible1) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                trailingIcon = {
                    val iconResId = if (passwordVisible1) {
                        R.drawable.visible_image
                    } else {
                        R.drawable.invisible_image
                    }

                    Image(
                        painter = painterResource(id = iconResId),
                        contentDescription = if (passwordVisible1) "Hide password" else "Show password",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { passwordVisible1 = !passwordVisible1 }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedTextColor = Color(0xFF006838),
                    unfocusedTextColor = Color(0xFF006838),
                    cursorColor = Color(0xFF006838)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    when {
                        email.isBlank() -> {
                            Toast.makeText(context, peeText, Toast.LENGTH_SHORT).show()
                        }
                        password.isBlank() -> {
                            Toast.makeText(context, pepText, Toast.LENGTH_SHORT).show()
                        }
                        confirmPassword.isBlank() -> {
                            Toast.makeText(context, pedText, Toast.LENGTH_SHORT).show()
                        }
                        password != confirmPassword -> {
                            Toast.makeText(context, pdmText, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            viewModel.register(email, password, {
                                navController.navigate(Screen.Login.route)
                            }, {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            })
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006838),
                    contentColor = Color.White
                )
            ) {
                Text(registerText)
            }
        }
    }
}
