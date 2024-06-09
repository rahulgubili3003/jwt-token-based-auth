package com.jwt.security.api.dto.request

data class AuthRequest(
    val username: String,
    val password: String
)