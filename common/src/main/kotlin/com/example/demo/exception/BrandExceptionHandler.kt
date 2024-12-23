package com.example.demo.exception

import com.example.demo.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import jakarta.persistence.PessimisticLockException

@ControllerAdvice
class GlobalExceptionHandler {

    // 특정 예외: IllegalArgumentException 처리
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException, request: WebRequest): ResponseEntity<ApiResponse<Any>> {
        return ResponseEntity.badRequest().body(
            ApiResponse(
                code = ErrorCode.INVALID_ARGUMENT.code,
                message = e.message ?: ErrorCode.INVALID_ARGUMENT.message,
                data = null
            )
        )
    }

    // 알 수 없는 예외 처리
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception, request: WebRequest): ResponseEntity<ApiResponse<Any>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiResponse(
                code = ErrorCode.UNEXPECTED_ERROR.code,
                message = ErrorCode.UNEXPECTED_ERROR.message,
                data = null
            )
        )
    }

    // 기타 예외 처리 (NullPointerException 등)
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException, request: WebRequest): ResponseEntity<ApiResponse<Any>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiResponse(
                code = ErrorCode.RUNTIME_EXCEPTION.code,
                message = ErrorCode.RUNTIME_EXCEPTION.message,
                data = null
            )
        )
    }


    @ExceptionHandler(PessimisticLockException::class)
    fun handlePessimisticLockException(e: PessimisticLockException): ResponseEntity<ApiResponse<Any>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiResponse(
                code = "LOCK_CONFLICT",
                message = "The resource is currently locked by another transaction.",
                data = null
            )
        )
    }

}
