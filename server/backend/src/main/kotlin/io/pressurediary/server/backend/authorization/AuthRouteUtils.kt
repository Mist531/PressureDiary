package io.pressurediary.server.backend.authorization

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import org.koin.core.component.KoinComponent
import java.util.UUID

class AuthRouteUtils : KoinComponent {
    suspend fun authUser(
        call: ApplicationCall,
        ifRight: suspend (id: UUID) -> Unit,
    ) {
        call.principal<JWTPrincipal>()?.payload?.getClaim(
            AuthUtil.JWT_NAME
        )?.asString()?.let { id ->
            ifRight(UUID.fromString(id))
        } ?: call.respond(HttpStatusCode.Unauthorized)
    }
}