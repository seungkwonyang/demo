package com.example.demo.repository

import com.example.demo.model.Brand
import com.example.demo.model.Product
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import jakarta.persistence.PersistenceContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
class ProductRepositoryTest @Autowired constructor(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository
) {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun `should lock product for update using PESSIMISTIC_WRITE`() {
        // Given: A brand and a product saved in the database
        val brand = brandRepository.save(Brand(name = "Test Brand"))
        val savedProduct = productRepository.save(
            Product(
                brand = brand,
                categoryCode = "SHOES",
                price = 100
            )
        )

        // When: The product is fetched with a pessimistic write lock
        val lockedProduct = productRepository.findByIdForUpdate(savedProduct.id!!)
        assertThat(lockedProduct).isNotNull
        assertThat(lockedProduct?.id).isEqualTo(savedProduct.id)
        assertThat(lockedProduct?.price).isEqualTo(100)

        // Then: Ensure the lock is applied
        val lockMode = entityManager.getLockMode(lockedProduct!!)
        assertThat(lockMode).isEqualTo(LockModeType.PESSIMISTIC_WRITE)
    }

    @Test
    fun `should return null if product does not exist`() {
        // When: Fetching a non-existing product with a pessimistic lock
        val nonExistentProduct = productRepository.findByIdForUpdate(999L)

        // Then: The result should be null
        assertThat(nonExistentProduct).isNull()
    }
}
