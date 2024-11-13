package com.example.farmbridge.ui.theme.Components


import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.farmbridge.ui.theme.Navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBar(
    title: String,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onChangeLanguageClick: () -> Unit,
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = buildAnnotatedString {
            withStyle(
                style= SpanStyle(
                    color = Color(0xFF006838),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold

                )
            ){
                append("F")
            }
            append("arm")

            withStyle(
                style= SpanStyle(
                    color = Color(0xFF006838),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,

                    )
            ){
                append("B")
            }
            append("ridge")
        },

            modifier = Modifier.padding(start = 60.dp)
        ) },
        navigationIcon = {
            IconButton(onClick = { expanded = true }) { // Show menu on click
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }, // Dismiss the dropdown when clicked outside
                modifier = Modifier.width(150.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Profile") },
                    onClick = {
                        onProfileClick() // Handle profile click
                        expanded = false // Close the dropdown
                    }
                )
                DropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = {
                        onSettingsClick() // Handle settings click
                        expanded = false // Close the dropdown
                    }
                )
                DropdownMenuItem(
                    text = { Text("Change Language") },
                    onClick = {
                        onChangeLanguageClick() // Handle change language click
                        expanded = false // Close the dropdown
                    }
                )
            }
        }
    )
}