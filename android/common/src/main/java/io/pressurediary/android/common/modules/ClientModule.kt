package io.pressurediary.android.common.modules

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.headers
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.pressurediary.android.common.data.stores.impl.TokensDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val HTTP_CLIENT_AUTH = "client_auth"
const val HTTP_CLIENT = "client_without_auth"
//TODO
const val HOST = "192.168.3.49:8082"//"ray-model-sensibly.ngrok-free.app"

internal val clientModule = module {
    factory(named(HTTP_CLIENT)) {
        HttpClient(CIO) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url {
                    //TODO
                    protocol = URLProtocol.HTTP
                    host = HOST
                }
                contentType(Json)
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
            }
            install(ContentNegotiation) {
                json(
                    json = getKoin().get<Json>()
                )
            }
        }
    }
    factory(named(HTTP_CLIENT_AUTH)) {
        runBlocking(Dispatchers.IO) {
            val tokensDataStore: TokensDataStore by inject()
            var access = ""
            CoroutineScope(Dispatchers.IO).launch {
                tokensDataStore.getStateFlow().collectLatest {
                    access = it?.access ?: ""
                    cancel()
                }
            }.join()
            HttpClient(CIO) {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
                install(DefaultRequest) {
                    url {
                        //TODO
                        protocol = URLProtocol.HTTP
                        host = HOST
                    }
                    contentType(Json)
                    headers {
                        append("Authorization", "Bearer $access")
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
                    json(
                        json = getKoin().get<Json>()
                    )
                }
            }
        }
    }
    single {
        Json {
            isLenient = true
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }
}