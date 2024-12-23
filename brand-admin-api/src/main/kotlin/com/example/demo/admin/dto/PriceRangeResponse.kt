package com.example.demo.admin.dto

data class PriceRangeResponse(
    val minPriceProduct: ProductPriceInfo,
    val maxPriceProduct: ProductPriceInfo
)
