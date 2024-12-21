package com.example.demo.service

import com.example.demo.dto.PriceRangeResponse
import com.example.demo.dto.ProductPriceInfo
import com.example.demo.model.Product
import com.example.demo.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findPriceRangeByCategory(categoryCode: String): PriceRangeResponse {
        val products = productRepository.findByCategoryCode(categoryCode)

        if (products.isEmpty()) {
            throw IllegalArgumentException("No products found for category code: $categoryCode")
        }

        val minProduct = products.minByOrNull { it.price }
        val maxProduct = products.maxByOrNull { it.price }

        return PriceRangeResponse(
            minPriceProduct = ProductPriceInfo(
                brand = minProduct!!.brand.name,
                price = minProduct.price
            ),
            maxPriceProduct = ProductPriceInfo(
                brand = maxProduct!!.brand.name,
                price = maxProduct.price
            )
        )
    }
}
