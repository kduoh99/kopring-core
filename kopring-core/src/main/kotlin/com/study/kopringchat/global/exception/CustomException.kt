package com.study.kopringchat.global.exception

class CustomException(
    val errorCode: CustomErrorCode
) : RuntimeException()
