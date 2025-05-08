package io.pressurediary.server.backend.configure

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.pressurediary.server.backend.authorization.AuthUtil
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.basic
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond

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
