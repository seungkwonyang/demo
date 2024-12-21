package com.example.demo.service

import com.example.demo.model.Brand
import com.example.demo.model.Product
import com.example.demo.repository.BrandRepository
import com.example.demo.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BrandService(
    private val brandRepository: BrandRepository,
    private val productRepository: ProductRepository
) {

    fun addBrand(brand: Brand): Brand {
        // 브랜드 이름 중복 체크
        if (brandRepository.findByName(brand.name) != null) {
            throw IllegalArgumentException("Brand '${brand.name}' already exists.")
        }
        return brandRepository.save(brand)
    }

    fun updateBrand(id: Long, updatedBrand: Brand): Brand {
        // 브랜드 존재 확인
        val existingBrand = brandRepository.findById(id).orElseThrow {
            IllegalArgumentException("Brand not found with id $id.")
        }
        // 브랜드 이름 업데이트
        return brandRepository.save(existingBrand.copy(name = updatedBrand.name))
    }

    fun deleteBrand(id: Long) {
        // 브랜드 존재 여부 확인
        if (!brandRepository.existsById(id)) {
            throw IllegalArgumentException("Brand not found with id $id.")
        }
        // 브랜드 삭제
        brandRepository.deleteById(id)
    }

    @Transactional
    fun addProductToBrand(brandId: Long, productRequest: Map<String, Any>): Product {
        val brand = brandRepository.findById(brandId).orElseThrow {
            IllegalArgumentException("Brand not found with id $brandId.")
        }

        val categoryCode = productRequest["categoryCode"] as? String
            ?: throw IllegalArgumentException("Category code is required.")
        val price = productRequest["price"] as? Int
            ?: throw IllegalArgumentException("Price is required.")

        val product = Product(
            brand = brand,
            categoryCode = categoryCode,
            price = price
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
