package com.example.demo.batch.dto

data class RedisBrandLowestProduct (
    val brand: String,
    val categoryPrice : List<RedisCategoryPrice>,
    val totalPrice: Int
)