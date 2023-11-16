package com.maktabah.maktabahyarsi.utils

import com.maktabah.maktabahyarsi.BuildConfig
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys


class JwtUtils {
    fun jwtDecoder(jwtToken: String): Claims {
        val secureKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
        return Jwts.parserBuilder()
            .setSigningKey(BuildConfig.SIGNING_KEY)
            .build()
            .parseClaimsJws(jwtToken)
            .body
    }
}