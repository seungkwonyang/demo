package com.example.demo.serving.dto.response

import com.example.demo.serving.dto.CategoryPriceDto

data class BrandLowestPriceResponse(
    val brand: String,
    val categoryPrice: List<CategoryPriceDto>,
    val totalPrice: Int
)