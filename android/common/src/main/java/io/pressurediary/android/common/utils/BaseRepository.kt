package io.pressurediary.android.common.utils

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.pressurediary.android.common.utils.errorflow.NetworkError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseRepository : KoinComponent {
    protected val repositoryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    protected suspend fun <T> repositoryContext(
        context: CoroutineContext = repositoryScope.coroutineContext,
        block: suspend CoroutineScope.() -> T
    ): T = withContext(
        context = context,
        block = block
    )

    suspend inline fun <reified R> HttpResponse.handleResponse(): Either<NetworkError, R> {
        return when (status.value) {
            in 200..299 -> {
                try {
                    body<R>().right()
                } catch (e: Throwable) {
                    NetworkError.JsonParseError("Ошибка десериализации: ${e.message}").left()
                }
            }

            else -> errorProcessingArrow(this).left()
        }
    }

    fun errorProcessingArrow(
        response: HttpResponse
    ): NetworkError = when (response.status) {
        HttpStatusCode.Unauthorized -> NetworkError.AuthenticationError("Неавторизованный доступ")

        HttpStatusCode.BadRequest -> NetworkError.RequestError(
            response.status.value,
            "Некорректный запрос"
        )

        HttpStatusCode.InternalServerError -> NetworkError.RequestError(
            response.status.value,
            "Ошибка сервера"
        )

        else -> NetworkError.UnknownError(
            "Неизвестная ошибка: ${response.status.description}"
        )
    }
}