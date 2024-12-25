package com.example.demo.serving.service

import com.example.demo.serving.dto.response.CategoryPriceResponse
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductService(
    private val redisTemplate: ReactiveRedisTemplate<String, Any>
) {

    fun getLowestPriceByCategory(): Mono<CategoryPriceResponse> {
        return redisTemplate.keys("products:category:*")
            .flatMap { redisTemplate.opsForValue().get(it) }
            .collectList()
            .flatMap { products ->
                val categoryPriceMap = mutableMapOf<String, Pair<String, Int>>()
                var totalPrice = 0

                products.forEach { productList ->
                    val category = (productList as List<Map<String, Any>>).first()["categoryCode"] as String
                    val lowestPriceProduct = productList.minByOrNull { (it["price"] as Int) }!!
                    val brand = (lowestPriceProduct["brand"] as Map<String, Any>)["name"] as String
                    val price = lowestPriceProduct["price"] as Int
                    totalPrice += price
                    categoryPriceMap[category] = brand to price
                }

                Mono.just(CategoryPriceResponse(categoryPriceMap, totalPrice))
            }
    }
}
