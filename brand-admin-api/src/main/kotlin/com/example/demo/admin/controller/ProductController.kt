package com.example.demo.admin.controller

import com.example.demo.dto.ApiResponse
import com.example.demo.admin.dto.PriceRangeResponse
import com.example.demo.admin.dto.ProductRequest
import com.example.demo.exception.ErrorCode
import com.example.demo.admin.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
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

    @PostMapping("/{brandId}/products")
    @Operation(
        summary = "Add a product to a brand",
        description = "Add a new product to the specified brand.",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Product successfully added"
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Brand not found")
        ]
    )
    fun addProductToBrand(
        @PathVariable @Parameter(description = "ID of the brand to add the product to") brandId: Long,
        @RequestBody productRequest: ProductRequest
    ): ResponseEntity<ApiResponse<Any>> {
        val savedProduct = productService.addProductToBrand(brandId, productRequest)
        return ResponseEntity.ok(
            ApiResponse(
                code = ErrorCode.SUCCESS.code,
                message = ErrorCode.SUCCESS.message,
                data = savedProduct
            )
        )
    }

    @PutMapping("/products/{productId}/price")
    @Operation(
        summary = "Update the price of a product",
        description = "Update the price of an existing product.",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Product price successfully updated"
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
        ]
    )
    fun updateProductPrice(
        @PathVariable @Parameter(description = "ID of the product to update the price for") productId: Long,
        @RequestParam @Parameter(description = "New price for the product") newPrice: Int
    ): ResponseEntity<ApiResponse<Any>> {
        val updatedProduct = productService.updateProductPrice(productId, newPrice)
        return ResponseEntity.ok(
            ApiResponse(
                code = ErrorCode.SUCCESS.code,
                message = ErrorCode.SUCCESS.message,
                data = updatedProduct
            )
        )
    }

    @DeleteMapping("/products/{productId}")
    @Operation(
        summary = "Delete a product",
        description = "Delete an existing product by its ID.",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Product successfully deleted"
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
        ]
    )
    fun deleteProduct(
        @PathVariable @Parameter(description = "ID of the product to delete") productId: Long
    ): ResponseEntity<ApiResponse<Any>> {
        productService.deleteProduct(productId)
        return ResponseEntity.ok(
            ApiResponse(
                code = ErrorCode.SUCCESS.code,
                message = ErrorCode.SUCCESS.message,
                data = null
            )
        )
    }
}
