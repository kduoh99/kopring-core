package com.study.kopringchat.global.auth.jwt

data class JwtInfo(
    val accessToken: String,
    val refreshToken: String
)
