package com.example.farmbridge.ui.theme.DataClasses


data class MarketState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: String? = null,
    val searchQuery: String = "",
    val priceRange: ClosedFloatingPointRange<Float> = 0f..10000f
)
