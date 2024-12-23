package com.example.demo.repository

import com.example.demo.model.Brand
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BrandRepository : JpaRepository<Brand, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Brand b WHERE b.name = :name")
    fun findByNameForUpdate(@Param("name") name: String): Brand?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Brand b WHERE b.id = :id")
    fun findByIdForUpdate(@Param("id") id: Long): Brand?
}
