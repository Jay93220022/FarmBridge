package com.example.farmbridge.ui.theme.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmbridge.ui.theme.DataClasses.MarketItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MarketViewModel : ViewModel() {

    val state = mutableStateOf<List<MarketItem>>(emptyList()) // Use a list of MarketItem

    init {
        getData() // Fetch data when the ViewModel is initialized
    }

    private fun getData() {
        viewModelScope.launch {
            state.value = getDataFromFireStore() // Update the list of MarketItems
        }
    }
}

suspend fun getDataFromFireStore(): List<MarketItem> {
    val db = FirebaseFirestore.getInstance()
    val marketItems = mutableListOf<MarketItem>() // Initialize a mutable list to store all items

    try {
        val result = db.collection("marketPrices")
            .get()
            .await()
            .documents

        // Add logging to see the documents retrieved
        Log.d("FirestoreData", "Documents: ${result.size}")
        result.forEach { document ->
            Log.d("FirestoreData", "Document Data: ${document.data}")
            document.toObject(MarketItem::class.java)?.let { fetchedItem ->
                Log.d("FirestoreData", "Crop Name: ${fetchedItem.cropName}, Price: ${fetchedItem.price}")
                marketItems.add(fetchedItem) // Add each MarketItem to the list
            }
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("FirestoreError", "getDataFromFireStore: $e")
    }

    return marketItems // Return the list of MarketItems
}
