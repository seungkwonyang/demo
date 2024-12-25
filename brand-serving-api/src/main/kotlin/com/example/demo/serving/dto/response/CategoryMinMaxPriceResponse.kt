package com.example.demo.serving.dto.response

import com.example.demo.serving.dto.ProductDetailDto

class CategoryMinMaxPriceResponse (
    val categoryCode: String,
    val minPriceProduct: ProductDetailDto,
    val maxPriceProduct: ProductDetailDto
)