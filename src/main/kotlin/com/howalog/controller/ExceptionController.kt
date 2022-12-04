package com.howalog.controller

import com.howalog.exception.HowalogException
import com.howalog.response.ErrorResponse
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(BAD_REQUEST)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorResponse {
        val errorResponse = ErrorResponse(400)
        e.fieldErrors.forEach {
            errorResponse.addValidation(it.field, it.defaultMessage)
        }
        return errorResponse
    }

    @ExceptionHandler(HowalogException::class)
    fun howalogException(e: HowalogException): ResponseEntity<ErrorResponse> {

        val statusCode = e.getStatusCode()
        val responseBody = ErrorResponse(statusCode, e.message)

        return ResponseEntity.status(statusCode)
            .body(responseBody)
    }
}