package com.example.demo.serving.service

import com.example.demo.batch.dto.RedisBrandLowestProduct
import com.example.demo.serving.dto.response.BrandLowestPriceResponse
import com.example.demo.serving.dto.CategoryPriceDto
import com.example.demo.serving.dto.ProductDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
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
                    val productDtoLists = objectMapper.readValue(
                        json,
                        objectMapper.typeFactory.constructCollectionType(List::class.java, ProductDto::class.java)
                    ) as? List<ProductDto> ?: emptyList()
                    productDtoLists
                } ?: emptyList()
                calculateLowestPrice(allProducts)
            }

    }

    fun calculateLowestPriceBySingleBrandNew(): Mono<BrandLowestPriceResponse> {
        return redisTemplate.opsForValue().get("lowest-price:brand")
            .flatMap { redisData ->
                try {
                    val json = objectMapper.writeValueAsString(redisData)
                    // Redis에서 JSON 데이터를 읽어와 객체로 변환
                    val redisResponse = objectMapper.readValue(
                        json, // Redis에서 반환된 데이터 (JSON 문자열)
                        RedisBrandLowestProduct::class.java // Redis에 저장된 클래스
                    )

                    // RedisBrandLowestProduct를 API 응답 형식인 BrandLowestPriceResponse로 변환
                    val result = BrandLowestPriceResponse(
                        brand = redisResponse.brand,
                        categoryPrice = redisResponse.categoryPrice.map {
                            CategoryPriceDto(it.category, it.price)
                        },
                        totalPrice = redisResponse.totalPrice
                    )
                    Mono.just(result)
                } catch (e: Exception) {
                    // JSON 변환 실패 시 예외 처리
                    Mono.error(RuntimeException("Failed to parse lowest price data: ${e.message}", e))
                }
            }
            .switchIfEmpty(
                // Redis에 키가 없을 경우 처리
                Mono.error(IllegalStateException("Lowest price data not found"))
            )
    }

    private fun calculateLowestPrice(productDtos: List<ProductDto>): BrandLowestPriceResponse {
        // 그룹화: 브랜드 -> 카테고리 -> 최저 가격
        val categoryPricesByBrand = productDtos.groupBy { it.brandDto.name }
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
        val categoryPriceDtos = bestBrand.value.map {
            CategoryPriceDto(it.key, it.value)
        }
        val totalPrice = bestBrand.value.values.sum()

        return BrandLowestPriceResponse(
            brand = brandName,
            categoryPrice = categoryPriceDtos,
            totalPrice = totalPrice
        )
    }

}
