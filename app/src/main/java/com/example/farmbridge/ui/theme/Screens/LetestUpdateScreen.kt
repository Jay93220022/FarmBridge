package com.example.farmbridge.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LatestUpdatesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Latest Updates") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF4CAF50)),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // Sample data for updates
        val updates = listOf(
            UpdateItem("New Crop Insurance Scheme Launched", "The government has introduced a new crop insurance scheme for small farmers.", "2024-11-12"),
            UpdateItem("Market Prices Increased for Wheat", "Recent market analysis shows an increase in the wheat market price by 10%.", "2024-11-10"),
            UpdateItem("Rainfall Expected in Maharashtra", "Farmers in Maharashtra can expect rainfall in the upcoming week.", "2024-11-08")
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Latest Agricultural News",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF388E3C)
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(updates) { update ->
                    UpdateCard(update = update)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun UpdateCard(update: UpdateItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = update.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = update.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF4CAF50)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatDate(update.date),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

// Sample data class for updates
data class UpdateItem(val title: String, val description: String, val date: String)

// Helper function to format date
fun formatDate(dateString: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = format.parse(dateString)
    val newFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return newFormat.format(date!!)
}
