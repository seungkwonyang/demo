package com.example.demo.serving.service

import com.example.demo.serving.dto.CategoryMinMaxPriceResponse
import com.example.demo.serving.dto.Product
import com.example.demo.serving.dto.ProductDetail
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CategoryPriceService(private val redisTemplate: ReactiveRedisTemplate<String, Any>) {
    private val objectMapper = jacksonObjectMapper()

    fun getCategoryPriceDetails(categoryCode: String): Mono<CategoryMinMaxPriceResponse> {
        val redisKey = "products:category:$categoryCode"

        return redisTemplate.opsForValue().get(redisKey)
            .map { redisData ->
                // Redis에서 가져온 데이터를 JSON으로 변환 후 List<Product>로 변환
                val json = objectMapper.writeValueAsString(redisData)
                val products: List<Product> = objectMapper.readValue(
                    json,
                    objectMapper.typeFactory.constructCollectionType(List::class.java, Product::class.java)
                )
                calculatePriceDetails(categoryCode, products)
            }
            .switchIfEmpty(
                Mono.error(IllegalArgumentException("No data found for category: $categoryCode"))
            )
    }

    private fun calculatePriceDetails(categoryCode: String, products: List<Product>): CategoryMinMaxPriceResponse {
        val minProduct = products.minByOrNull { it.price }
            ?: throw IllegalStateException("No products available in category $categoryCode")
        val maxProduct = products.maxByOrNull { it.price }
            ?: throw IllegalStateException("No products available in category $categoryCode")

        return CategoryMinMaxPriceResponse(
            categoryCode = categoryCode,
            minPriceProduct = ProductDetail(minProduct.brand.name, minProduct.price),
            maxPriceProduct = ProductDetail(maxProduct.brand.name, maxProduct.price)
        )
    }
}
