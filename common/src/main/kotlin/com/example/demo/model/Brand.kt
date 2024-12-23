package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "brands")
data class Brand(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var name: String = ""
) {
    constructor() : this(0, "")
}
