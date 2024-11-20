package com.example.farmbridge.ui.theme.Screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun CropSelectionScreen(onCropSelected: (String) -> Unit) {
    val crops = listOf("Tomato", "Potato", "Rice", "Wheat")
    var selectedCrop by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Select Crop to Recognize Disease")
        Spacer(modifier = Modifier.height(16.dp))

        // Use LazyColumn with itemsIndexed for correct list rendering
        LazyColumn {
            itemsIndexed(crops) { index, crop ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedCrop = crop
                            onCropSelected(crop)
                        }
                        .padding(12.dp)
                ) {
                    Text(text = crop)
                    Spacer(modifier = Modifier.weight(1f))
                    if (selectedCrop == crop) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Selected")
                    }
                }
            }
        }
    }
}

// Function to pick an image from the gallery
private fun pickImageFromGallery(
    activityResultLauncher: ActivityResultLauncher<Intent>
) {
    // Create an Intent to open the gallery
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    activityResultLauncher.launch(intent)
}

@Composable
fun UploadImageButton(onImagePicked: (Uri) -> Unit) {
    val context = LocalContext.current
    // Register an ActivityResultLauncher for image selection
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // The URI of the selected image
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    // Handle the picked image URI by passing it to onImagePicked
                    onImagePicked(it)
                }
            }
        }
    )

    // UI to trigger image picking
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Pick a plant image to recognize the disease")
        Button(onClick = {
            // Call the function to open the gallery
            pickImageFromGallery(activityResultLauncher)
        }) {
            Text(text = "Upload Plant Image")
        }
    }
}
