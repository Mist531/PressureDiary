package com.backend.configure

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.dataconversion.DataConversion
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            Json {
                encodeDefaults = true
                explicitNulls = false
            }
        )
    }
    install(DataConversion) {
        convert<UUID> {
            decode { values ->
                values.singleOrNull()?.let {
                    UUID.fromString(it)
                } ?: UUID.randomUUID()
            }
            encode { uuid ->
                listOf(uuid.toString())
            }
        }
    }
}
