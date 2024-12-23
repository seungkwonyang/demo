package com.example.demo.exception

enum class ErrorCode(val code: String, val message: String) {
    SUCCESS("SUCCESS", "Operation completed successfully."),
    INVALID_ARGUMENT("INVALID_ARGUMENT", "Invalid argument provided."),
    BRAND_NOT_FOUND("BRAND_NOT_FOUND", "Brand not found."),
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", "Product not found."),
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "An unexpected error occurred."),
    RUNTIME_EXCEPTION("RUNTIME_EXCEPTION", "A runtime exception occurred.")
}
