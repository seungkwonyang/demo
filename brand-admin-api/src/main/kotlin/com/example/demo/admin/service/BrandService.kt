package com.example.demo.admin.service
import com.example.demo.model.Brand
import com.example.demo.repository.BrandRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BrandService(
    private val brandRepository: BrandRepository
) {

    @Transactional
    fun addBrand(brand: Brand): Brand {
        // 1. 락을 사용하여 브랜드 중복 검증
        val existingBrand = brandRepository.findByNameForUpdate(brand.name)
        if (existingBrand != null) {
            throw IllegalArgumentException("Brand '${brand.name}' already exists.")
        }
        return brandRepository.save(brand)
    }

    @Transactional
    fun updateBrand(id: Long, updatedBrand: Brand): Brand {
        // 1. 락을 사용하여 브랜드 조회 및 업데이트
        val existingBrand = brandRepository.findByIdForUpdate(id) ?: throw IllegalArgumentException(
            "Brand not found with id $id."
        )
        return brandRepository.save(existingBrand.copy(name = updatedBrand.name))
    }

    @Transactional
    fun deleteBrand(id: Long) {
        // 1. 락을 사용하여 브랜드 존재 확인
        val existingBrand = brandRepository.findByIdForUpdate(id) ?: throw IllegalArgumentException(
            "Brand not found with id $id."
        )
        // 2. 브랜드 삭제
        brandRepository.delete(existingBrand)
    }
}
