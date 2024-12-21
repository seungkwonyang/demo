package com.example.demo.controller

import com.example.demo.dto.ApiResponse
import com.example.demo.model.Brand
import com.example.demo.service.BrandService
import com.example.demo.exception.ErrorCode
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/brands")
@Tag(name = "Brand API", description = "APIs for managing brands and products")
class BrandController(private val brandService: BrandService) {

    @PostMapping
    @Operation(
        summary = "Add a new brand",
        description = "Create a new brand in the system.",
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "Brand successfully created",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponse::class))]
            ),
            SwaggerApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    fun addBrand(@RequestBody brand: Brand): ResponseEntity<ApiResponse<Brand>> {
        val savedBrand = brandService.addBrand(brand)
        return ResponseEntity.ok(
            ApiResponse(
                code = ErrorCode.SUCCESS.code,
                message = ErrorCode.SUCCESS.message,
                data = savedBrand
            )
        )
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update a brand",
        description = "Update an existing brand by its ID.",
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "Brand successfully updated",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponse::class))]
            ),
            SwaggerApiResponse(responseCode = "404", description = "Brand not found")
        ]
    )
    fun updateBrand(
        @PathVariable @Parameter(description = "ID of the brand to update") id: Long,
        @RequestBody brand: Brand
    ): ResponseEntity<ApiResponse<Brand>> {
        val updatedBrand = brandService.updateBrand(id, brand)
        return ResponseEntity.ok(
            ApiResponse(
                code = ErrorCode.SUCCESS.code,
                message = ErrorCode.SUCCESS.message,
                data = updatedBrand
            )
        )
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a brand",
        description = "Delete an existing brand by its ID.",
        responses = [
            SwaggerApiResponse(responseCode = "200", description = "Brand successfully deleted"),
            SwaggerApiResponse(responseCode = "404", description = "Brand not found")
        ]
    )
    fun deleteBrand(
        @PathVariable @Parameter(description = "ID of the brand to delete") id: Long
    ): ResponseEntity<ApiResponse<Any>> {
        brandService.deleteBrand(id)
        return ResponseEntity.ok(
            ApiResponse(
                code = ErrorCode.SUCCESS.code,
                message = ErrorCode.SUCCESS.message,
                data = null
            )
        )
    }

    @PostMapping("/{brandId}/products")
    @Operation(
        summary = "Add a product to a brand",
        description = "Add a new product to the specified brand.",
        responses = [
            SwaggerApiResponse(responseCode = "200", description = "Product successfully added"),
            SwaggerApiResponse(responseCode = "404", description = "Brand not found")
        ]
    )
    fun addProductToBrand(
        @PathVariable @Parameter(description = "ID of the brand to add the product to") brandId: Long,
        @RequestBody productRequest: Map<String, Any>
    ): ResponseEntity<ApiResponse<Any>> {
        val savedProduct = brandService.addProductToBrand(brandId, productRequest)
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
            SwaggerApiResponse(responseCode = "200", description = "Product price successfully updated"),
            SwaggerApiResponse(responseCode = "404", description = "Product not found")
        ]
    )
    fun updateProductPrice(
        @PathVariable @Parameter(description = "ID of the product to update the price for") productId: Long,
        @RequestParam @Parameter(description = "New price for the product") newPrice: Int
    ): ResponseEntity<ApiResponse<Any>> {
        val updatedProduct = brandService.updateProductPrice(productId, newPrice)
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
            SwaggerApiResponse(responseCode = "200", description = "Product successfully deleted"),
            SwaggerApiResponse(responseCode = "404", description = "Product not found")
        ]
    )
    fun deleteProduct(
        @PathVariable @Parameter(description = "ID of the product to delete") productId: Long
    ): ResponseEntity<ApiResponse<Any>> {
        brandService.deleteProduct(productId)
        return ResponseEntity.ok(
            ApiResponse(
                code = ErrorCode.SUCCESS.code,
                message = ErrorCode.SUCCESS.message,
                data = null
            )
        )
    }
}
