package com.example.demo.repository

import com.example.demo.model.Brand
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand, Long> {
    fun findByName(name: String): Brand?
}
