package com.example.farmbridge.ui.theme.DataClasses

data class Product(
    val id: String = "",
    val farmerId: String = "",
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val unit: String = "",
    val images: List<String> = emptyList(),
    val location: String = "",
    val dateAdded: Long = System.currentTimeMillis()
)
