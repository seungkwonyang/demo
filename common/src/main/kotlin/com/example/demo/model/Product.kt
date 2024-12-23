package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    val brand: Brand,

    @Column(name = "category_code", nullable = false)
    val categoryCode: String,

    @Column(nullable = false)
    val price: Int
){
    constructor() : this(0, Brand(), "", 0)
}
