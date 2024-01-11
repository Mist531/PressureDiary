package com.mist.common.modules

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal val clientModule = module {
    factory {
        HttpClient(CIO) {
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
                url {
                    protocol = URLProtocol.HTTP
                    host = "0.0.0.0:8082"
                }
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
            }
            install(ContentNegotiation) {
                json(getKoin().get())
            }
        }
    }
    single {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }
}