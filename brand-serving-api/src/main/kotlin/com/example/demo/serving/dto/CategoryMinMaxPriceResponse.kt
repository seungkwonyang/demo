package com.example.demo.serving.dto

class CategoryMinMaxPriceResponse (
    val categoryCode: String,
    val minPriceProduct: ProductDetail,
    val maxPriceProduct: ProductDetail
)