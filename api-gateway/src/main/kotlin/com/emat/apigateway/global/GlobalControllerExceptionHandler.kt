package com.emat.apigateway.global

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors: Map<String, String> = ex.bindingResult.fieldErrors.associateBy({ it.field },
            { it.defaultMessage ?: "Invalid value" })
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<Map<String, String>> {
        val error: Map<String, String> = mapOf("error" to (ex.message ?: "An error occurred"))
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleGenericException(ex: NoHandlerFoundException): ResponseEntity<Map<String, String>> {
        val error: Map<String, String> = mapOf(
            "error" to ( "No URL found for this request " + ex.requestURL)
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

}