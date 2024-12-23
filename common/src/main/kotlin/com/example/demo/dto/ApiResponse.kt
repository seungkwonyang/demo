package com.example.demo.dto

data class ApiResponse<T>(
    val code: String,          // 에러 코드 (성공 시 "SUCCESS")
    val message: String,       // 메시지
    val data: T? = null        // 응답 데이터 (성공 시 포함)
)
