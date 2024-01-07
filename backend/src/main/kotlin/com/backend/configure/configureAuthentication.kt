package com.backend.configure

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.backend.authorization.AuthUtil
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureAuthentication() {
    install(Authentication) {
        basic("basic") {
            jwt("jwt") {
                realm = System.getenv("JWT_REALM") ?: "test_realm"
                val jwtAudience = AuthUtil.jwtAudience
                val secret = AuthUtil.secret
                val domain = AuthUtil.domain
                verifier(
                    JWT.require(Algorithm.HMAC256(secret))
                        .withAudience(jwtAudience)
                        .withIssuer(domain)
                        .build()
                )
                validate { credential ->
                    if (credential.payload.audience.contains(jwtAudience))
                        JWTPrincipal(credential.payload)
                    else null
                }
                challenge { _, _ ->
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }
}
