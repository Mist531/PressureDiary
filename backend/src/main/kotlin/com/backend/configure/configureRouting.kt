package com.backend.configure

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

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
