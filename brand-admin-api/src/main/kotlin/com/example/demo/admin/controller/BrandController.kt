package com.example.demo.admin.controller

import com.example.demo.dto.ApiResponse
import com.example.demo.exception.ErrorCode
import com.example.demo.model.Brand
import com.example.demo.admin.service.BrandService
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


}
