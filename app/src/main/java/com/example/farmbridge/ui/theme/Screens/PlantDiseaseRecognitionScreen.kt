package com.example.farmbridge.ui.theme.Screens

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf

import com.example.farmbridge.ui.theme.Screens.CropSelectionScreen
import com.example.farmbridge.ui.theme.Screens.UploadImageButton
import com.example.farmbridge.ui.theme.Screens.RecognitionResultScreen
import com.example.farmbridge.ui.theme.ViewModels.PlantDiseaseViewModel

@Composable
fun PlantDiseaseRecognitionScreen(viewModel: PlantDiseaseViewModel) {
    var selectedCrop by remember { mutableStateOf<String>("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Observe the result from the ViewModel
    val result by viewModel.result.observeAsState()

    // Get context using LocalContext
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        // Crop Selection
        Text(text = "Select Crop to Recognize Disease")
        Spacer(modifier = Modifier.height(16.dp))

        // Crop selection dropdown or buttons
        CropSelectionScreen(onCropSelected = { crop ->
            selectedCrop = crop
            viewModel.setCrop(crop)  // Send the selected crop to ViewModel
        })

        Spacer(modifier = Modifier.height(32.dp))

        // Image Upload
        UploadImageButton(onImagePicked = { uri ->
            imageUri = uri
            uri?.let {
                if (selectedCrop.isNotEmpty()) {
                    // Ensure a crop is selected before recognizing the disease
                    viewModel.recognizeDisease(it, selectedCrop, context) // Pass context here
                }
            }
        })

        Spacer(modifier = Modifier.height(32.dp))

        // Display Recognition Result
        if (result != null && result!!.isNotEmpty()) {
            RecognitionResultScreen(result = result!!)
        } else {
            // Placeholder if no result is available yet
            Text(text = "Please select a crop and upload an image to recognize the disease.",)
        }
    }
}
