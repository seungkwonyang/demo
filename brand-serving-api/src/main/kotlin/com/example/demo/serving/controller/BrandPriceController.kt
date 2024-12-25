package com.example.demo.serving.controller

import com.example.demo.serving.dto.response.BrandLowestPriceResponse
import com.example.demo.serving.dto.response.LowestPriceResponse
import com.example.demo.serving.service.BrandService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/brands")
class BrandPriceController(private val brandPriceService: BrandService) {

    @Operation(
        summary = "Get lowest price by single brand",
        description = "Fetch the lowest price, brand, category prices, and total price for purchasing all categories from a single brand."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully fetched lowest price by brand",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = LowestPriceResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json"
                    )
                ]
            )
        ]
    )
//    @GetMapping("/lowest-price")
//    fun getLowestPriceBySingleBrand(): Mono<LowestPriceResponse> {
//        return brandPriceService.calculateLowestPriceBySingleBrand()
//            .map { LowestPriceResponse(it) }
//            .onErrorResume { e ->
//                Mono.error(RuntimeException("Failed to calculate lowest price: ${e.message}"))
//            }
//    }
    @GetMapping("/lowest-price")
    fun getLowestPrice(): Mono<LowestPriceResponse> {
        return brandPriceService.calculateLowestPriceBySingleBrandNew()
            .map { LowestPriceResponse(it) }
            .onErrorResume { e ->
                Mono.error(RuntimeException("Failed to calculate lowest price: ${e.message}"))
            }
    }
}
