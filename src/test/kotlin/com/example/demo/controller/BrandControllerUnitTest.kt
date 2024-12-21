package com.example.demo.controller

import com.example.demo.dto.ApiResponse
import com.example.demo.model.Brand
import com.example.demo.model.Product
import com.example.demo.service.BrandService
import com.example.demo.exception.ErrorCode
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity

class BrandControllerUnitTest {

    private val brandService: BrandService = mockk(relaxed = true)
    private val brandController = BrandController(brandService)

    // Helper Method for ApiResponse Validation
    private fun <T> assertApiResponse(
        response: ResponseEntity<ApiResponse<T>>,
        expectedData: T?,
        expectedCode: ErrorCode,
        expectedStatusCode: Int
    ) {
        assertThat(response.statusCodeValue).isEqualTo(expectedStatusCode)
        assertThat(response.body?.data).isEqualTo(expectedData)
        assertThat(response.body?.code).isEqualTo(expectedCode.code)
        assertThat(response.body?.message).isEqualTo(expectedCode.message)
    }

    // Helper Method for Mock Brand
    private fun createMockBrand(id: Long = 1L, name: String = "Mock Brand") = Brand(id = id, name = name)

    // Helper Method for Mock Product
    private fun createMockProduct(
        id: Long = 1L,
        brandId: Long = 1L,
        categoryCode: String = "Shoes",
        price: Int = 100
    ) = Product(
        id = id,
        brand = createMockBrand(brandId, "Mock Brand"),
        categoryCode = categoryCode,
        price = price
    )

    @Test
    fun `should add a new brand`() {
        // Given
        val newBrand = createMockBrand(name = "New Brand")
        every { brandService.addBrand(newBrand) } returns newBrand

        // When
        val response = brandController.addBrand(newBrand)

        // Then
        assertApiResponse(response, newBrand, ErrorCode.SUCCESS, 200)
        verify(exactly = 1) { brandService.addBrand(newBrand) }
    }

    @Test
    fun `should update an existing brand`() {
        // Given
        val brandId = 1L
        val updatedBrand = createMockBrand(name = "Updated Brand")
        every { brandService.updateBrand(brandId, updatedBrand) } returns updatedBrand

        // When
        val response = brandController.updateBrand(brandId, updatedBrand)

        // Then
        assertApiResponse(response, updatedBrand, ErrorCode.SUCCESS, 200)
        verify(exactly = 1) { brandService.updateBrand(brandId, updatedBrand) }
    }

    @Test
    fun `should delete a brand`() {
        // Given
        val brandId = 1L
        every { brandService.deleteBrand(brandId) } returns Unit

        // When
        val response = brandController.deleteBrand(brandId)

        // Then
        assertApiResponse(response, null, ErrorCode.SUCCESS, 200)
        verify(exactly = 1) { brandService.deleteBrand(brandId) }
    }

    @Test
    fun `should add a product to a brand`() {
        // Given
        val brandId = 1L
        val productRequest = mapOf("category" to "Shoes", "price" to 100)
        val savedProduct = createMockProduct(categoryCode = "Shoes", price = 100)
        every { brandService.addProductToBrand(brandId, productRequest) } returns savedProduct

        // When
        val response = brandController.addProductToBrand(brandId, productRequest)

        // Then
        assertApiResponse(response, savedProduct, ErrorCode.SUCCESS, 200)
        verify(exactly = 1) { brandService.addProductToBrand(brandId, productRequest) }
    }

    @Test
    fun `should update product price`() {
        // Given
        val productId = 1L
        val newPrice = 150
        val updatedProduct = createMockProduct(price = newPrice)
        every { brandService.updateProductPrice(productId, newPrice) } returns updatedProduct

        // When
        val response = brandController.updateProductPrice(productId, newPrice)

        // Then
        assertApiResponse(response, updatedProduct, ErrorCode.SUCCESS, 200)
        verify(exactly = 1) { brandService.updateProductPrice(productId, newPrice) }
    }

    @Test
    fun `should delete a product`() {
        // Given
        val productId = 1L
        every { brandService.deleteProduct(productId) } returns Unit

        // When
        val response = brandController.deleteProduct(productId)

        // Then
        assertApiResponse(response, null, ErrorCode.SUCCESS, 200)
        verify(exactly = 1) { brandService.deleteProduct(productId) }
    }
}
