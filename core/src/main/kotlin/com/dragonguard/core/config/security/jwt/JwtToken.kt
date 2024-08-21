package com.dragonguard.core.config.security.jwt

data class JwtToken(
    var accessToken: String,
    var refreshToken: String,
)
