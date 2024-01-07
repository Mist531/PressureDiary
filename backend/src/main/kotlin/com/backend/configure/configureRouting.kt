package com.backend.configure

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Exception> { call, cause ->
            call.respond(
                status = call.response.status() ?: HttpStatusCode.BadRequest,
                message = cause.message.orEmpty()
            )
        }
    }
}
