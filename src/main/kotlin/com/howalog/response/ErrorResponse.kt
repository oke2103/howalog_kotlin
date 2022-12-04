package com.howalog.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY

@JsonInclude(NON_EMPTY)
data class ErrorResponse(
    val code: Int,
    val message: String? = "잘못된 요청입니다.",
    val validation: MutableMap<String, String?> = hashMapOf()
) {

    fun addValidation(errorField: String, errorMessage: String?) {
        validation[errorField] = errorMessage
    }
}