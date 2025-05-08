package io.pressurediary.server.backend.authorization

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import io.ktor.server.auth.jwt.JWTPrincipal
import java.util.Date
import java.util.UUID
import kotlin.time.Duration.Companion.days

object AuthUtil {

    val jwtAudience = System.getenv("JWT_AUDIENCE") ?: "test_audience"
    val secret = System.getenv("JWT_SECRET") ?: "test_secret"
    val domain = System.getenv("JWT_DOMAIN") ?: "test_domain"

    fun buildAccess(id: String): String = JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(domain)
        .withClaim(JWT_NAME, id)
        .withExpiresAt(Date(System.currentTimeMillis() + 1.days.inWholeMilliseconds))
        .sign(Algorithm.HMAC256(secret))

    fun buildRefresh(id: String): String = JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(domain)
        .withClaim(JWT_NAME, id)
        .withExpiresAt(Date(System.currentTimeMillis() + 7.days.inWholeMilliseconds))
        .sign(Algorithm.HMAC256(secret))

    fun getUUIDFromRefreshToken(refreshToken: String): UUID? {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm)
                .withAudience(jwtAudience)
                .withIssuer(domain)
                .build()

            val decodedJWT = verifier.verify(refreshToken)
            JWTPrincipal(decodedJWT).payload.getClaim(JWT_NAME)
                .asString()?.let(UUID::fromString)
        } catch (e: JWTVerificationException) {
            throw Exception("Неверный токен")
        }
    }

    const val JWT_NAME = "userId"
}