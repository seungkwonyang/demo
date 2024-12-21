package com.example.demo.repository

import com.example.demo.model.Brand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class BrandRepositoryTest @Autowired constructor(
    private val brandRepository: BrandRepository
) {

    @Test
    fun `should find brand by name`() {
        // Given: A brand is saved in the database
        val brand = Brand(name = "Test Brand")
        brandRepository.save(brand)

        // When: We search for the brand by name
        val foundBrand = brandRepository.findByName("Test Brand")

        // Then: The brand is found and matches the saved brand
        assertThat(foundBrand).isNotNull
        assertThat(foundBrand?.name).isEqualTo("Test Brand")
    }

    @Test
    fun `should return null when brand name does not exist`() {
        // When: We search for a brand name that doesn't exist
        val foundBrand = brandRepository.findByName("Nonexistent Brand")

        // Then: No brand is found
        assertThat(foundBrand).isNull()
    }
}
