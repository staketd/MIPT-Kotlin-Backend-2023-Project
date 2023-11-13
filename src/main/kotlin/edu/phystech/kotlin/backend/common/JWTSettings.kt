package edu.phystech.kotlin.backend.common

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import java.util.*

data class JWTSettings(
    val secret: String,
    val audience: String,
    val issuer: String,
    val realm: String
) {
    fun generateToken(login: String, ttlMillis: Long): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withExpiresAt(Date(System.currentTimeMillis() + ttlMillis))
        .withClaim("login", login)
        .sign(Algorithm.HMAC256(secret))

    fun getVerifier() = JWT.require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun verify(credential: JWTCredential): JWTPrincipal? {
        return if (credential.payload.audience.contains(audience)
            && credential.payload.getClaim("login").asString() != "") {
            JWTPrincipal(credential.payload)
        } else {
            null
        }
    }
}