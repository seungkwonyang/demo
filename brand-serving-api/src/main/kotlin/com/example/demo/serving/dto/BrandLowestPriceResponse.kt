package com.example.demo.serving.dto

data class BrandLowestPriceResponse(
    val brand: String,
    val categoryPriceList: List<CategoryPrice>,
    val totalPrice: Int
)