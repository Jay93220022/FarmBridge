package com.example.farmbridge.ui.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmbridge.ui.theme.LanguageViewModel
import com.example.farmbridge.ui.theme.ViewModels.MarketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCropScreen(marketViewModel: MarketViewModel = viewModel(), context: android.content.Context, languageViewModel: LanguageViewModel) {
    var cropName by remember { mutableStateOf("") }
    var cropQuantity by remember { mutableStateOf("") }
    var manualPrice by remember { mutableStateOf("") }
    val suggestedPrice = marketViewModel.suggestedPrice.value
    val farmerCrops = marketViewModel.farmerCrops.collectAsState()

    // States for edit dialog
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedCrop by remember { mutableStateOf<MarketViewModel.FarmerCrop?>(null) }

    // States for delete confirmation
    var showDeleteDialog by remember { mutableStateOf(false) }
    var cropToDelete by remember { mutableStateOf<MarketViewModel.FarmerCrop?>(null) }

    // Observe the success states for showing toasts
    val cropAddedSuccess by remember { marketViewModel.cropAddedSuccess }
    val cropUpdatedSuccess by remember { marketViewModel.cropUpdatedSuccess }
    val cropDeletedSuccess by remember { marketViewModel.cropDeletedSuccess }
    val green = Color(0xFF4CAF50)
    val currentLanguage = languageViewModel.currentLanguage.collectAsState(initial = "English").value

    // Show toasts for different actions
    LaunchedEffect(cropAddedSuccess, cropUpdatedSuccess, cropDeletedSuccess) {
        when {
            cropAddedSuccess -> {
                Toast.makeText(context, "Crop added successfully!", Toast.LENGTH_SHORT).show()
                marketViewModel.cropAddedSuccess.value = false
                cropName = ""
                cropQuantity = ""
                manualPrice = ""
            }
            cropUpdatedSuccess -> {
                Toast.makeText(context, "Crop updated successfully!", Toast.LENGTH_SHORT).show()
                marketViewModel.cropUpdatedSuccess.value = false
            }
            cropDeletedSuccess -> {
                Toast.makeText(context, "Crop deleted successfully!", Toast.LENGTH_SHORT).show()
                marketViewModel.cropDeletedSuccess.value = false
            }
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = when (currentLanguage) {
                            "Hindi" -> "अपना फसल जोड़ें"
                            "Marathi" -> "आपला पिक जोडा"
                            else -> "Add Your Crop"
                        }
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = green,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .padding(bottom = 70.dp)
        ) {
            item {
                // Input Form Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        OutlinedTextField(
                            value = cropName,
                            onValueChange = {
                                cropName = it
                                if (cropName.isNotBlank()) {
                                    marketViewModel.getSuggestedPriceForCrop(cropName)
                                }
                            }, singleLine = true,
                            label = {
                                Text(
                                    text = when (currentLanguage) {
                                        "Hindi" -> "फसल का नाम"
                                        "Marathi" -> "पिकाचे नाव"
                                        else -> "Crop Name"
                                    }
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (suggestedPrice != null) {
                            // Show suggested price card if available
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = when (currentLanguage) {
                                            "Hindi" -> "वर्तमान बाजार मूल्य"
                                            "Marathi" -> "सध्याची बाजार किंमत"
                                            else -> "Current Market Price"
                                        },
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                    Text(
                                        text = "₹${suggestedPrice.price}/quintal",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        } else {
                            // Show manual price input if no suggestion available
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = when (currentLanguage) {
                                            "Hindi" -> "कोई सुझाया गया मूल्य उपलब्ध नहीं है"
                                            "Marathi" -> "कोणतेही सुचवलेले मूल्य उपलब्ध नाही"
                                            else -> "No suggested price available"
                                        },
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = manualPrice, singleLine = true,
                                        onValueChange = { manualPrice = it },
                                        label = {
                                            Text(
                                                text = when (currentLanguage) {
                                                    "Hindi" -> "बाजार मूल्य (₹/क्विंटल) दर्ज करें"
                                                    "Marathi" -> "बाजार किंमत (₹/क्विंटल) प्रविष्ट करा"
                                                    else -> "Enter Market Price (₹/quintal)"
                                                }
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = cropQuantity,
                            singleLine = true,
                            onValueChange = { cropQuantity = it },
                            label = {
                                Text(
                                    text = when (currentLanguage) {
                                        "Hindi" -> "मात्रा (क्विंटल में)"
                                        "Marathi" -> "प्रमाण (क्विंटल मध्ये)"
                                        else -> "Quantity (in quintals)"
                                    }
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (cropName.isNotBlank() && cropQuantity.isNotBlank() &&
                                    (suggestedPrice != null || manualPrice.isNotBlank())
                                ) {
                                    marketViewModel.addCropToDatabase(
                                        cropName,
                                        cropQuantity,
                                        suggestedPrice?.price ?: manualPrice
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        when (currentLanguage) {
                                            "Hindi" -> "कृपया सभी फ़ील्ड भरें"
                                            "Marathi" -> "कृपया सर्व फील्ड भरा"
                                            else -> "Please fill in all fields"
                                        },
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = green,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = when (currentLanguage) {
                                    "Hindi" -> "फसल जोड़ें"
                                    "Marathi" -> "पिक जोडा"
                                    else -> "Add Crop"
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = when (currentLanguage) {
                        "Hindi" -> "आपके द्वारा जोड़ी गई फसलें"
                        "Marathi" -> "तुमच्याद्वारे जोडलेली पिके"
                        else -> "Your Added Crops"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            items(farmerCrops.value) { crop ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = crop.cropName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${crop.quantity} Quintals",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Row {
                            IconButton(onClick = {
                                selectedCrop = crop
                                showEditDialog = true
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = when (currentLanguage) {
                                        "Hindi" -> "संपादित करें"
                                        "Marathi" -> "संपादित करा"
                                        else -> "Edit"
                                    }
                                )
                            }

                            IconButton(onClick = {
                                cropToDelete = crop
                                showDeleteDialog = true
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = when (currentLanguage) {
                                        "Hindi" -> "हटाएं"
                                        "Marathi" -> "काढा"
                                        else -> "Delete"
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Edit Dialog and Delete Confirmation Dialog remain the same...


    // Edit Dialog
    if (showEditDialog && selectedCrop != null) {
        var editCropName by remember { mutableStateOf(selectedCrop!!.cropName) }
        var editQuantity by remember { mutableStateOf(selectedCrop!!.quantity) }
        var editPrice by remember { mutableStateOf(selectedCrop!!.price) }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit Crop") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editCropName,
                        singleLine = true,
                        onValueChange = { editCropName = it },
                        label = { Text("Crop Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editQuantity,
                        singleLine = true,
                        onValueChange = { editQuantity = it },
                        label = { Text("Quantity") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editPrice,
                        singleLine = true,
                        onValueChange = { editPrice = it },
                        label = { Text("Price") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        marketViewModel.updateCrop(
                            selectedCrop!!.id,
                            editCropName,
                            editQuantity,
                            editPrice
                        )
                        showEditDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog && cropToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                cropToDelete = null
            },
            title = { Text("Delete Crop") },
            text = {
                Text("Are you sure you want to delete ${cropToDelete!!.cropName}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        marketViewModel.deleteCrop(cropToDelete!!.id)
                        showDeleteDialog = false
                        cropToDelete = null
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Red
                    )
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        cropToDelete = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }


}