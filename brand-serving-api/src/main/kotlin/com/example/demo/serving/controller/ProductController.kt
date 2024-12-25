package com.example.demo.serving.controller

import com.example.demo.serving.service.ProductService
import com.example.demo.serving.dto.response.CategoryPriceResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@Tag(name = "Product API", description = "API for managing products and categories")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/categories/lowest-prices")
    @Operation(
        summary = "Get lowest price by category",
        description = "Fetches the lowest price and brand for each category, along with the total price.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved data",
                content = [Content(schema = Schema(implementation = CategoryPriceResponse::class))]
            ),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    fun getLowestPriceByCategory(): Mono<CategoryPriceResponse> {
        return productService.getLowestPriceByCategory()
    }
}
