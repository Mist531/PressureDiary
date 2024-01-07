package com.backend.configure

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.dataconversion.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import java.util.*

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
