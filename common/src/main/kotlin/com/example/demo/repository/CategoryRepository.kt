package com.example.demo.repository

import com.example.demo.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByCode(code: String): Category?
}
