package com.example.farmbridge.ui.theme.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Register(modifier: Modifier = Modifier) {
    var textFieldState by remember {
        mutableStateOf("")
    }

    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {


    OutlinedTextField(value = textFieldState,
        label ={
            Text(text = "Enter Your Mobile Number", color = Color.Black)
        },
        onValueChange = { textFieldState = it
        },
        singleLine = true,


    )

}}