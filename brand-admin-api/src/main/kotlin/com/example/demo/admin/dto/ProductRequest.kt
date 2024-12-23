package com.example.demo.admin.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProductRequest(
    @field:NotBlank(message = "Category code is required.")
    val categoryCode: String,

    @field:NotNull(message = "Price is required.")
    @field:Min(value = 1, message = "Price must be greater than zero.")
    val price: Int
)