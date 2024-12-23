package com.example.demo.serving.service

import com.example.demo.serving.dto.BrandLowestPriceResponse
import com.example.demo.serving.dto.CategoryPrice
import com.example.demo.serving.dto.Product
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BrandService(private val redisTemplate: ReactiveRedisTemplate<String, Any>) {

    private val objectMapper = jacksonObjectMapper()


    fun calculateLowestPriceBySingleBrand(): Mono<BrandLowestPriceResponse> {
        return redisTemplate.keys("products:category:*")
            .collectList() // Flux<String>을 Mono<List<String>>로 변환
            .flatMap { keys ->
                redisTemplate.opsForValue().multiGet(keys) // List<String>을 multiGet에 전달
            }
            .map { redisDataList ->
                val allProducts = redisDataList?.flatMap { redisData ->

                    val json = objectMapper.writeValueAsString(redisData)
                    val productList = objectMapper.readValue(
                        json,
                        objectMapper.typeFactory.constructCollectionType(List::class.java, Product::class.java)
                    ) as? List<Product> ?: emptyList()
                    productList
                } ?: emptyList()
                calculateLowestPrice(allProducts)
            }

    }

    private fun calculateLowestPrice(products: List<Product>): BrandLowestPriceResponse {
        // 그룹화: 브랜드 -> 카테고리 -> 최저 가격
        val categoryPricesByBrand = products.groupBy { it.brand.name }
            .mapValues { (_, products) ->
                products.groupBy { it.categoryCode }
                    .mapValues { (_, categoryProducts) ->
                        categoryProducts.minOfOrNull { it.price } ?: Int.MAX_VALUE
                    }
            }

        // 최저 총액 브랜드 선택
        val bestBrand = categoryPricesByBrand.minByOrNull { (_, categoryPrices) -> categoryPrices.values.sum() }
            ?: throw IllegalStateException("No products available")

        val brandName = bestBrand.key
        val categoryPrices = bestBrand.value.map {
            CategoryPrice(it.key, it.value)
        }
        val totalPrice = bestBrand.value.values.sum()

        return BrandLowestPriceResponse(
            brand = brandName,
            categoryPriceList = categoryPrices,
            totalPrice = totalPrice
        )
    }

}
