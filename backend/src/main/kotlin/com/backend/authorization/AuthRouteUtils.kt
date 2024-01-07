package com.backend.authorization

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.core.component.KoinComponent
import java.util.*

class AuthRouteUtils : KoinComponent {
    suspend fun authUser(
        call: ApplicationCall,
        ifRight: suspend (id: UUID) -> Unit,
    ) {
        call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()?.let { id ->
            ifRight(UUID.fromString(id))
        } ?: call.respond(HttpStatusCode.Unauthorized)
    }
}