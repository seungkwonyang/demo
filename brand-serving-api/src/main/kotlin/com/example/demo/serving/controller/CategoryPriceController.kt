package com.example.demo.serving.controller

import com.example.demo.serving.dto.CategoryMinMaxPriceResponse
import com.example.demo.serving.dto.CategoryPriceResponse
import com.example.demo.serving.service.CategoryPriceService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/categories")
class CategoryPriceController(private val categoryPriceService: CategoryPriceService) {

    @Operation(
        summary = "Get min and max price details by category",
        description = "Fetches the brand and price details for the lowest and highest priced products in a given category."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved category price details"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid category code"
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            )
        ]
    )
    @GetMapping("/{categoryCode}/price-details")
    fun getCategoryPriceDetails(
        @PathVariable @Parameter(description = "Category code to fetch price details for") categoryCode: String
    ): Mono<CategoryMinMaxPriceResponse> {
        return categoryPriceService.getCategoryPriceDetails(categoryCode)
    }
}