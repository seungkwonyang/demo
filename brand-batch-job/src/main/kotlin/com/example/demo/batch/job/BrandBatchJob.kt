package com.example.demo.batch.job

import com.example.demo.batch.dto.RedisBrandLowestProduct
import com.example.demo.batch.dto.RedisCategoryPrice
import com.example.demo.model.Product
import com.example.demo.repository.BrandRepository
import com.example.demo.repository.ProductRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Component

@Component
class RedisBatchJob(
    private val brandRepository: BrandRepository,
    private val productRepository: ProductRepository,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(RedisBatchJob::class.java)
    }
    // 1분 주기로 실행
    @Scheduled(fixedRate = 10000)
    fun syncDataToRedis() {
        syncBrands()
        syncProductsByCategory()
        calculateAndStoreLowestPrice()
    }

    private fun syncBrands() {
        val brands = brandRepository.findAll()
        redisTemplate.opsForValue().set("brands:all", brands)
        log.info("Brands synced to Redis: ${brands.count()} items")
    }

    private fun syncProductsByCategory() {
        val allProducts = productRepository.findAll()
        val productsByCategory = allProducts.groupBy { it.categoryCode }

        productsByCategory.forEach { (categoryCode, products) ->
            redisTemplate.opsForValue().set("products:category:$categoryCode", products)
        }
        log.info("Products synced to Redis by category: ${productsByCategory.keys.size} categories")
    }

    private fun calculateAndStoreLowestPrice() {
        val lowestPrice = calculateLowestPriceByBrand(productRepository.findAll())
        redisTemplate.opsForValue().set("lowest-price:brand", lowestPrice)
        log.info("Lowest price data has been updated in Redis")
    }

    private fun calculateLowestPriceByBrand(products: List<Product>) : RedisBrandLowestProduct{
        val categoryPricesByBrand = products.groupBy { it.brand.name }
            .mapValues { (_, brandProducts) ->
                brandProducts.groupBy { it.categoryCode }
                    .mapValues { (_, categoryProducts) ->
                        categoryProducts.minOf { it.price }
                    }
            }

        val bestBrand = categoryPricesByBrand.minByOrNull { (_, categoryPrices) ->
            categoryPrices.values.sum()
        } ?: throw IllegalStateException("No products available")

        val brandName = bestBrand.key
        val categoryPrices = bestBrand.value.map { RedisCategoryPrice(it.key, it.value) }
        val totalPrice = bestBrand.value.values.sum()

        return RedisBrandLowestProduct(
            brand = brandName,
            categoryPrice = categoryPrices,
            totalPrice = totalPrice
        )
    }
}
