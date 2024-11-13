package com.example.farmbridge.ui.theme.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmbridge.ui.theme.DataClasses.MarketItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MarketViewModel : ViewModel() {
    val cropAddedSuccess = mutableStateOf(false)
    val cropUpdatedSuccess = mutableStateOf(false)
    val cropDeletedSuccess = mutableStateOf(false)
    val state = mutableStateOf<List<MarketItem>>(emptyList())
    val suggestedPrice = mutableStateOf<MarketItem?>(null)

    init {
        getData()
    }

    // Updated FarmerCrop data class with ID
    data class FarmerCrop(
        val id: String = "",  // Added document ID
        val cropName: String = "",
        val quantity: String = "",
        val price: String = ""
    )

    private val _farmerCrops = MutableStateFlow<List<FarmerCrop>>(emptyList())
    val farmerCrops: StateFlow<List<FarmerCrop>> = _farmerCrops

    init {
        getFarmerCrops()
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

    fun addCropToDatabase(cropName: String, quantity: String, price: String) {
        viewModelScope.launch {
            val cropData = hashMapOf(
                "cropName" to cropName,
                "quantity" to quantity,
                "price" to price
            )
            FirebaseFirestore.getInstance()
                .collection("Crops")
                .add(cropData)
                .addOnSuccessListener {
                    cropAddedSuccess.value = true
                }
                .addOnFailureListener {
                    cropAddedSuccess.value = false
                }
        }
    }

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
                                id = doc.id,  // Include the document ID
                                cropName = doc.getString("cropName") ?: "",
                                quantity = doc.getString("quantity") ?: "",
                                price = doc.getString("price") ?: ""
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