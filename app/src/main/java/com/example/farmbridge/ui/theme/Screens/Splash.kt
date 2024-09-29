package com.example.farmbridge.ui.theme.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Splash(modifier: Modifier = Modifier) {
    LazyColumn{
    items(999999999){
            Text(text = "\uD83D\uDE05  \uD83D\uDE05  \uD83D\uDE05  \uD83D\uDE05  \uD83D\uDE05  \uD83D\uDE05  \uD83D\uDE05  ", fontWeight = FontWeight.Bold, fontSize = 24.sp, fontStyle = FontStyle.Italic )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}