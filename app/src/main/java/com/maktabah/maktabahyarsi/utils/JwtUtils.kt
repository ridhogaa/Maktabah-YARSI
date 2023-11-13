package com.maktabah.maktabahyarsi.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts


class JwtUtils {
    fun jwtDecoder(jwtToken: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey("maktabahyarsi")
            .build()
            .parseClaimsJws(jwtToken)
            .body
    }
}