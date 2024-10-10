// LoginScreen.kt
package com.example.farmbridge.ui.theme.Screens

import android.transition.Scene
import android.widget.Toast

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.farmbridge.R
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.Navigation.PreferenceHelper
import com.example.farmbridge.ui.theme.Navigation.Screen

import com.example.farmbridge.ui.theme.ViewModels.AuthViewModel
import com.example.farmbridge.ui.theme.uitheme.FarmBridgeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
authViewModel: AuthViewModel,
navController: NavController,
languageViewModel: LanguageViewModel,

) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val currentLanguage =
        languageViewModel.currentLanguage.collectAsState(initial = "English").value
    val emailText = when (currentLanguage) {
        "Hindi" -> "ईमेल"
        "Marathi" -> "ईमेल"
        else -> "Email"
    }
    val passwordText = when (currentLanguage) {
        "Hindi" -> "पासवर्ड"
        "Marathi" -> "पासवर्ड"
        else -> "Password"
    }
    val loginText = when (currentLanguage) {
        "Hindi" -> "लॉगिन"
        "Marathi" -> "लॉगिन"
        else -> "Login"
    }
    val donthaveText = when (currentLanguage) {
        "Hindi" -> "खाता नहीं है? पंजीकृत करें"
        "Marathi" -> "खाते नाही का? नोंदणी करणे"
        else -> "Don't have Account ? Register"
    }
    val pedText = when (currentLanguage) {
        "Hindi" -> "कृपया विवरण दर्ज करें"
        "Marathi" -> "कृपया तपशील प्रविष्ट करा"
        else -> "Please Enter Details"
    }
    val forgotPassText = when (currentLanguage) {
        "Hindi" -> "पासवर्ड भूल गए?"
        "Marathi" -> "पासवर्ड विसरला?"
        else -> "Forgot Password?"
    }
    val peeText = when (currentLanguage) {
        "Hindi" -> "कृपया ईमेल आईडी दर्ज करें"
        "Marathi" -> "कृपया ईमेल आयडी प्रविष्ट करा"
        else -> "Please Enter Email Id"
    }
    val pepText = when (currentLanguage) {
        "Hindi" -> "कृपया पासवर्ड दर्ज करें"
        "Marathi" -> "कृपया पासवर्ड प्रविष्ट करा"
        else -> "Please Enter Password"
    }

    FarmBridgeTheme {
        Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier.padding(start = 60.dp, bottom = 20.dp)
            )



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(Modifier.height(50.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            text = emailText,
                            fontWeight = FontWeight.Bold,
                           color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =MaterialTheme.colorScheme.onBackground,
                        unfocusedBorderColor =MaterialTheme.colorScheme.onBackground,
                        focusedTextColor = Color(0xFF006838),
                        unfocusedTextColor = Color(0xFF006838),
                        cursorColor = Color(0xFF006838)
                    ), singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text(
                            text = passwordText,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =MaterialTheme.colorScheme.onBackground,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        focusedTextColor = Color(0xFF006838),
                        unfocusedTextColor = Color(0xFF006838),
                        cursorColor = Color(0xFF006838)
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                                .clickable { passwordVisible = !passwordVisible },
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Only one error message display block
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

//                if (preferenceHelper.isLoggedIn()) {
//
//                    navController.navigate(Screen.Dashboard.route) {
//                        popUpTo(Screen.Login.route) { inclusive = true }
//                    }
//                }
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            authViewModel.login(email, password, {
                              //  preferenceHelper.setLoggedIn(true)

                                navController.navigate(Screen.Dashboard.route) {
                                // popUpTo(Screen.Login.route) { inclusive = true }

                                }
                            }, {
                                errorMessage = it
                            })
                        } else {
                            if (email.isEmpty() && password.isEmpty()) {
                                Toast.makeText(context, pedText, Toast.LENGTH_SHORT).show()
                            } else if (email.isEmpty()) {
                                Toast.makeText(context, peeText, Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, pepText, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006838),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(loginText)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = {
                    navController.navigate(Screen.Register.route)
                }) {
                    Text(donthaveText, color = Color(0xFF006838))
                }

                TextButton(onClick = { navController.navigate(Screen.ForgotPassword.route) }) {
                    Text(forgotPassText, color = Color(0xFF006838))
                }
            }
        }
    }
}