package com.example.demo.service

import com.example.demo.model.Brand
import com.example.demo.model.Product
import com.example.demo.repository.BrandRepository
import com.example.demo.repository.ProductRepository
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class BrandServiceUnitTest {

    private val brandRepository: BrandRepository = mockk(relaxed = true)
    private val productRepository: ProductRepository = mockk(relaxed = true)
    private val brandService = BrandService(brandRepository, productRepository)

    @Test
    fun `should add a new brand`() {
        // Given
        val newBrand = Brand(name = "New Brand")
        every { brandRepository.findByName(newBrand.name) } returns null
        every { brandRepository.save(newBrand) } returns newBrand

        // When
        val savedBrand = brandService.addBrand(newBrand)

        // Then
        assertThat(savedBrand.name).isEqualTo("New Brand")
        verify { brandRepository.findByName(newBrand.name) }
        verify { brandRepository.save(newBrand) }
    }

    @Test
    fun `should throw exception when adding duplicate brand`() {
        // Given
        val existingBrand = Brand(name = "Existing Brand")
        every { brandRepository.findByName(existingBrand.name) } returns existingBrand

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            brandService.addBrand(existingBrand)
        }
        assertThat(exception.message).isEqualTo("Brand 'Existing Brand' already exists.")
        verify { brandRepository.findByName(existingBrand.name) }
    }

    @Test
    fun `should update an existing brand`() {
        // Given
        val existingBrand = Brand(id = 1L, name = "Old Brand")
        val updatedBrand = Brand(name = "Updated Brand")
        every { brandRepository.findById(1L) } returns Optional.of(existingBrand)
        every { brandRepository.save(any()) } answers { firstArg() }

        // When
        val result = brandService.updateBrand(1L, updatedBrand)

        // Then
        assertThat(result.name).isEqualTo("Updated Brand")
        verify { brandRepository.findById(1L) }
        verify { brandRepository.save(any()) }
    }

    @Test
    fun `should throw exception when updating non-existent brand`() {
        // Given
        every { brandRepository.findById(1L) } returns Optional.empty()

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            brandService.updateBrand(1L, Brand(name = "Updated Brand"))
        }
        assertThat(exception.message).isEqualTo("Brand not found with id 1.")
        verify { brandRepository.findById(1L) }
    }

    @Test
    fun `should delete a brand`() {
        // Given
        every { brandRepository.existsById(1L) } returns true
        every { brandRepository.deleteById(1L) } just Runs

        // When
        brandService.deleteBrand(1L)

        // Then
        verify { brandRepository.existsById(1L) }
        verify { brandRepository.deleteById(1L) }
    }

    @Test
    fun `should throw exception when deleting non-existent brand`() {
        // Given
        every { brandRepository.existsById(1L) } returns false

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            brandService.deleteBrand(1L)
        }
        assertThat(exception.message).isEqualTo("Brand not found with id 1.")
        verify { brandRepository.existsById(1L) }
    }

    @Test
    fun `should add a product to a brand`() {
        // Given
        val brand = Brand(id = 1L, name = "Test Brand")
        val productRequest = mapOf("categoryCode" to "Shoes", "price" to 100)
        val savedProduct = Product(
            id = 1L,
            brand = brand,
            categoryCode = "Shoes",
            price = 100
        )
        every { brandRepository.findById(1L) } returns Optional.of(brand)
        every { productRepository.save(any()) } returns savedProduct

        // When
        val result = brandService.addProductToBrand(1L, productRequest)

        // Then
        assertThat(result.categoryCode).isEqualTo("Shoes")
        assertThat(result.price).isEqualTo(100)
        verify { brandRepository.findById(1L) }
        verify { productRepository.save(any()) }
    }

    @Test
    fun `should throw exception when adding a product to non-existent brand`() {
        // Given
        every { brandRepository.findById(1L) } returns Optional.empty()

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            brandService.addProductToBrand(1L, mapOf("categoryCode" to "Shoes", "price" to 100))
        }
        assertThat(exception.message).isEqualTo("Brand not found with id 1.")
        verify { brandRepository.findById(1L) }
    }

    @Test
    fun `should update product price`() {
        // Given
        val product = Product(
            id = 1L,
            brand = Brand(id = 1L, name = "Test Brand"),
            categoryCode = "Shoes",
            price = 100
        )
        every { productRepository.findByIdForUpdate(1L) } returns product
        every { productRepository.save(any()) } answers { firstArg() }

        // When
        val updatedProduct = brandService.updateProductPrice(1L, 150)

        // Then
        assertThat(updatedProduct.price).isEqualTo(150)
        verify { productRepository.findByIdForUpdate(1L) }
        verify { productRepository.save(any()) }
    }

    @Test
    fun `should delete a product`() {
        // Given
        every { productRepository.existsById(1L) } returns true
        every { productRepository.deleteById(1L) } just Runs

        // When
        brandService.deleteProduct(1L)

        // Then
        verify { productRepository.existsById(1L) }
        verify { productRepository.deleteById(1L) }
    }
}
