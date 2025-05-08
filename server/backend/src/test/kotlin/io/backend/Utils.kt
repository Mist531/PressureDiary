package io.backend

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.ktor.utils.io.KtorDsl
import io.pressurediary.server.api.models.LoginModel
import io.pressurediary.server.api.models.PostUserRequestModel
import io.pressurediary.server.backend.createDefaultInfoUser
import io.pressurediary.server.backend.myApplicationModule
import kotlin.coroutines.EmptyCoroutineContext

internal inline fun <reified T> HttpRequestBuilder.setBodyTest(
    body: T,
    token: String? = null,
) {
    contentType(ContentType.Application.Json)
    setBody(body)
    token?.let {
        header(HttpHeaders.Authorization, "Bearer $it")
    }
}

internal val testUser = PostUserRequestModel(
    email = "test@mail.com",
    password = "test",
    firstName = "test",
    lastName = "test",
    dateOfBirth = LocalDate.now()
)

internal val loginModel = LoginModel(
    email = testUser.email,
    password = testUser.password
)

@KtorDsl
internal fun testMyApplication(
    block: suspend ApplicationTestBuilder.(
        myClient: HttpClient
    ) -> Unit
) = testApplication(EmptyCoroutineContext) {
    //if (GlobalContext.getOrNull() == null) {
    application {
        myApplicationModule()
    }
    //}

    createDefaultInfoUser(
        userEmail = loginModel.email,
        userPass = loginModel.password,
    )

    val myClient = createClient {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json()
        }
    }

    block(myClient)
}