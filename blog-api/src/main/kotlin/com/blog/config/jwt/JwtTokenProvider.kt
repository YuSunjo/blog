package com.blog.config.jwt

import com.blog.exception.JwtException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.xml.bind.DatatypeConverter
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec


@Component
class JwtTokenProvider {

    val secretKey = "secure enough for the HS256 algorithm_velog_admin_secret_key_v2"
    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

    fun createToken(subject: String): String {
        val claims: Claims = Jwts.claims().setSubject(subject)
        val now = Date()
        val tokenValidTime = 1000L * 60 * 60
        val validity = Date(now.time + tokenValidTime)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(createKey(), signatureAlgorithm)
            .compact()
    }

    private fun createKey(): Key {
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey)
        return SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)
    }

    fun getSubject(token: String?): String? {
        try {
            return Jwts.parserBuilder().setSigningKey(createKey()).build().parseClaimsJws(token).body.subject
        } catch (exception: Exception) {
            throw JwtException("올바르지 않은 jwt입니다.")
        }
    }
}