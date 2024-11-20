package com.example.farmbridge.ui.theme.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecognitionResultScreen(result: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Plant Disease Prediction")
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Predicted Disease: $result")
    }
}
