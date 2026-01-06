package com.study.kopringchat.global.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.study.kopringchat.global.common.BaseResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class ErrorResponse(
    private val objectMapper: ObjectMapper
) {
    fun send(response: HttpServletResponse, errorCode: CustomErrorCode) {
        val baseResponse = BaseResponse<Unit>(errorCode)

        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.writer.write(objectMapper.writeValueAsString(baseResponse.body))
    }
}
