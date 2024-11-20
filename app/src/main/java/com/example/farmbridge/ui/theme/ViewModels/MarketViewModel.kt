package com.example.farmbridge.ui.theme.ViewModels

import android.app.Application
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmbridge.ui.theme.DataClasses.MarketItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MarketViewModel(application: Application) : AndroidViewModel(application) {
    val cropAddedSuccess = mutableStateOf(false)
    val cropUpdatedSuccess = mutableStateOf(false)
    val cropDeletedSuccess = mutableStateOf(false)
    val state = mutableStateOf<List<MarketItem>>(emptyList())
    val suggestedPrice = mutableStateOf<MarketItem?>(null)
    val harvestDate = mutableStateOf("")

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl = _imageUrl.asStateFlow()

    private val _isUploading = MutableStateFlow(false)
    val isUploading = _isUploading.asStateFlow()

    private val _uploadError = MutableStateFlow<String?>(null)
    val uploadError = _uploadError.asStateFlow()

    private val _farmerCrops = MutableStateFlow<List<FarmerCrop>>(emptyList())
    val farmerCrops: StateFlow<List<FarmerCrop>> = _farmerCrops.asStateFlow()

    init {
        getData()
        getFarmerCrops()
    }

    data class FarmerCrop(
        val id: String = "",
        val cropName: String = "",
        val quantity: String = "",
        val price: String = "",
        val imageUrl: String? = null,
        val harvestDate: String = ""
    )


    fun updateCrop(cropId: String, cropName: String, quantity: String, price: String) {
        viewModelScope.launch {
            val cropData = hashMapOf(
                "cropName" to cropName,
                "quantity" to quantity,
                "price" to price
            )

            FirebaseFirestore.getInstance()
                .collection("Crops")
                .document(cropId)
                .update(cropData.toMap())
                .addOnSuccessListener {
                    cropUpdatedSuccess.value = true
                }
                .addOnFailureListener {
                    cropUpdatedSuccess.value = false
                }
        }
    }

    fun deleteCrop(cropId: String) {
        viewModelScope.launch {
            FirebaseFirestore.getInstance()
                .collection("Crops")
                .document(cropId)
                .delete()
                .addOnSuccessListener {
                    cropDeletedSuccess.value = true
                }
                .addOnFailureListener {
                    cropDeletedSuccess.value = false
                }
        }
    }
    private fun getData() {
        viewModelScope.launch {
            state.value = getDataFromFireStore()
        }
    }

    fun getSuggestedPriceForCrop(cropName: String) {
        viewModelScope.launch {
            suggestedPrice.value = getSingleMarketPriceFromFirestore(cropName)
        }
    }

    private suspend fun getDataFromFireStore(): List<MarketItem> {
        val db = FirebaseFirestore.getInstance()
        val marketItems = mutableListOf<MarketItem>()

        try {
            val result = db.collection("marketPrices")
                .get()
                .await()
                .documents

            result.forEach { document ->
                document.toObject(MarketItem::class.java)?.let { fetchedItem ->
                    marketItems.add(fetchedItem)
                }
            }
        } catch (e: Exception) {
            Log.d("FirestoreError", "Error fetching data: $e")
        }

        return marketItems
    }

    private suspend fun getSingleMarketPriceFromFirestore(cropName: String): MarketItem? {
        val db = FirebaseFirestore.getInstance()
        return try {
            val document = db.collection("marketPrices")
                .whereEqualTo("cropName", cropName)
                .limit(1)
                .get()
                .await()
                .documents
                .firstOrNull()

            document?.toObject(MarketItem::class.java)
        } catch (e: Exception) {
            Log.d("FirestoreError", "Error fetching suggested price: $e")
            null
        }
    }

     suspend fun uploadImageToStorage(imageUri: Uri): String? {
        return try {
            val timestamp = System.currentTimeMillis()
            val fileExtension = getFileExtension(imageUri)
            val fileName = "crop_image_${timestamp}.$fileExtension"

            val storageRef = FirebaseStorage.getInstance()
                .reference
                .child("crop_images/$fileName")

            storageRef.putFile(imageUri).await()
            val downloadUrl = storageRef.downloadUrl.await()
            downloadUrl.toString() // Return the URL
        } catch (e: Exception) {
            Log.e("ImageUpload", "Image upload failed", e)
            null // Return null if upload fails
        }
    }

    private fun getFileExtension(uri: Uri): String {
        val contentResolver = getApplication<Application>().contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ?: "jpg"
    }

    fun clearImageData() {
        _imageUrl.value = null
        _uploadError.value = null
    }
    fun addCropToDatabase(
        cropName: String,
        quantity: String,
        price: String,
        imageUri: Uri
    ) {
        viewModelScope.launch {
            try {
                _isUploading.value = true

                // Upload image and get URL
                val uploadedImageUrl = uploadImageToStorage(imageUri)
                if (uploadedImageUrl == null) {
                    _uploadError.value = "Image upload failed"
                    return@launch
                }

                // Add crop data
                val cropData = hashMapOf(
                    "cropName" to cropName,
                    "quantity" to quantity,
                    "price" to price,
                    "imageUrl" to uploadedImageUrl,
                    "harvestDate" to harvestDate.value
                )

                FirebaseFirestore.getInstance()
                    .collection("Crops")
                    .add(cropData)
                    .addOnSuccessListener {
                        cropAddedSuccess.value = true
                        Log.d("FirestoreSuccess", "Crop added successfully!")
                    }
                    .addOnFailureListener { e ->
                        cropAddedSuccess.value = false
                        Log.e("FirestoreError", "Error adding crop: $e")
                    }
            } catch (e: Exception) {
                Log.e("UploadError", "Error adding crop details: $e")
                _uploadError.value = e.message ?: "Unknown error"
            } finally {
                _isUploading.value = false
            }
        }
    }

    private fun getFarmerCrops() {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            try {
                db.collection("Crops")
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            Log.w("FirestoreError", "Listen failed.", e)
                            return@addSnapshotListener
                        }

                        val cropsList = snapshot?.documents?.mapNotNull { doc ->
                            FarmerCrop(
                                id = doc.id,
                                cropName = doc.getString("cropName") ?: "",
                                quantity = doc.getString("quantity") ?: "",
                                price = doc.getString("price") ?: "",
                                imageUrl = doc.getString("imageUrl"),
                                harvestDate = doc.getString("harvestDate") ?: ""
                            )
                        } ?: emptyList()

                        _farmerCrops.value = cropsList
                    }
            } catch (e: Exception) {
                Log.e("FirestoreError", "Error fetching farmer crops", e)
            }
        }
    }
}
