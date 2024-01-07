package com.backend.authorization

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*
import kotlin.time.Duration.Companion.days

object AuthUtil {

    val jwtAudience = System.getenv("JWT_AUDIENCE") ?: "test_audience"
    val secret = System.getenv("JWT_SECRET") ?: "test_secret"
    val domain = System.getenv("JWT_DOMAIN") ?: "test_domain"

    fun buildAccess(id: String): String = JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(domain)
        .withClaim("userId", id)
        .withExpiresAt(Date(System.currentTimeMillis() + 1.days.inWholeMilliseconds))
        .sign(Algorithm.HMAC256(secret))

    fun buildRefresh(id: String): String = JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(domain)
        .withClaim("userId", id)
        .withExpiresAt(Date(System.currentTimeMillis() + 7.days.inWholeMilliseconds))
        .sign(Algorithm.HMAC256(secret))
}