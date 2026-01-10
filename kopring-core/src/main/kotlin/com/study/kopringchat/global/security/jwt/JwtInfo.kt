package com.study.kopringchat.global.security.jwt

data class JwtInfo(
    val accessToken: String,
    val refreshToken: String
)
