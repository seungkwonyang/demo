package com.example.demo.admin.service

import com.example.demo.admin.dto.PriceRangeResponse
import com.example.demo.admin.dto.ProductPriceInfo
import com.example.demo.admin.dto.ProductRequest
import com.example.demo.model.Product
import com.example.demo.repository.BrandRepository
import com.example.demo.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val productRepository: ProductRepository,
                     private val brandRepository: BrandRepository) {

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

    @Transactional
    fun addProductToBrand(brandId: Long, productRequest: ProductRequest): Product {
        // 1. 브랜드 검증
        val brand = brandRepository.findById(brandId).orElseThrow {
            IllegalArgumentException("Brand not found with id $brandId.")
        }

        // 2. 비관적 락 또는 기존 상품 확인
        val existingProduct = productRepository.findByIdForUpdate(brandId)
        if (existingProduct != null) {
            val updatedProduct = existingProduct.copy(
                categoryCode = productRequest.categoryCode,
                price = productRequest.price
            )
            return productRepository.save(updatedProduct)
        }

        // 3. 새로운 상품 생성
        val product = Product(
            brand = brand,
            categoryCode = productRequest.categoryCode,
            price = productRequest.price
        )

        return productRepository.save(product)
    }

    fun deleteProduct(productId: Long) {
        // 상품 존재 여부 확인
        if (!productRepository.existsById(productId)) {
            throw IllegalArgumentException("Product not found with id $productId.")
        }
        // 상품 삭제
        productRepository.deleteById(productId)
    }

    @Transactional
    fun updateProductPrice(productId: Long, newPrice: Int): Product {
        // 비관적 락을 사용하여 상품 조회
        val product = productRepository.findByIdForUpdate(productId)
            ?: throw IllegalArgumentException("Product not found with id $productId.")

        // 상품 가격 업데이트
        val updatedProduct = product.copy(price = newPrice)

        // 변경된 상품 저장
        return productRepository.save(updatedProduct)
    }
}
