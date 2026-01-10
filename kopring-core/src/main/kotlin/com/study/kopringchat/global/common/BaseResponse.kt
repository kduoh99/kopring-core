package com.study.kopringchat.global.common

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.http.HttpStatus

@JsonPropertyOrder("status", "state", "message", "data")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponse<T>(
    val status: Int,
    val state: String? = null,
    val message: String,
    val data: T? = null
) {
    constructor(status: HttpStatus, state: String? = null, message: String, data: T? = null) : this(
        status = status.value(),
        state = state,
        message = message,
        data = data
    )
}
