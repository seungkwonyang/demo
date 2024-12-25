package com.example.demo.serving.dto.response

data class CategoryPriceResponse(
    val categories: Map<String, Pair<String, Int>>, // 카테고리, 브랜드, 가격
    val totalPrice: Int
)
