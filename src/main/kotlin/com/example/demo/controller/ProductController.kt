package com.example.demo.controller

import com.example.demo.dto.ApiResponse
import com.example.demo.dto.PriceRangeResponse
import com.example.demo.exception.ErrorCode
import com.example.demo.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping("/price-range")
    fun getPriceRangeByCategory(@RequestParam categoryCode: String): ResponseEntity<ApiResponse<PriceRangeResponse>> {
        val result = productService.findPriceRangeByCategory(categoryCode)
        return ResponseEntity.ok(
            ApiResponse(
                code = ErrorCode.SUCCESS.code,
                message = ErrorCode.SUCCESS.message,
                data = result
            )
        )
    }
}
