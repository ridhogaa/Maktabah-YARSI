package com.maktabah.maktabahyarsi.utils

import android.util.Log
import com.maktabah.maktabahyarsi.BuildConfig
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import javax.crypto.spec.SecretKeySpec


class JwtUtils {
    fun decodedJwtLogin(secret: String, token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(SecretKeySpec("maktabahyarsi".toByteArray(), SignatureAlgorithm.HS256.jcaName))
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            e.printStackTrace()
            Log.i("EXPIRED", "Token Anda telah kedaluwarsa")
            null
        }
    }
}