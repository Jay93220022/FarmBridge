package com.example.farmbridge.ui.theme.Screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.farmbridge.ui.theme.ViewModels.MarketViewModel

@Composable
fun CustomerProductsScreen(viewModel: MarketViewModel,modifier: Modifier) {
    // Collecting the list of farmer crops from the ViewModel's state flow
    val farmerCrops by viewModel.farmerCrops.collectAsState(initial = emptyList())

    // Main container for the products screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title for the screen
        Text(
            text = "Available Crops",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // LazyColumn to display the list of crops
        LazyColumn {
            items(farmerCrops) { crop ->
                // Display each crop in a card
                FarmerCropCard(crop)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun FarmerCropCard(crop: MarketViewModel.FarmerCrop) {
    // Card displaying crop details with a square aspect ratio
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)  // Ensures square cards
            .padding(8.dp),

    ) {
        // Column to display crop details
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Crop name
            Text(text = "Crop Name: ${crop.cropName}", style = MaterialTheme.typography.bodyLarge)
            // Crop quantity
            Text(text = "Quantity: ${crop.quantity}", style = MaterialTheme.typography.bodyLarge)
            // Crop price
            Text(text = "Price: ₹${crop.price}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
