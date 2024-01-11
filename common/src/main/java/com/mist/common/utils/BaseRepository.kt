package com.mist.common.utils

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.component.KoinComponent

abstract class BaseRepository : KoinComponent {
    protected val repositoryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    suspend inline fun <reified R> HttpResponse.handleResponse(): Either<NetworkError, R> {
        return when (status.value) {
            in 200..299 -> {
                try {
                    body<R>().right()
                } catch (e: Exception) {
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

        HttpStatusCode.UpgradeRequired -> NetworkError.UpgradeRequiredError("Требуется обновление")

        HttpStatusCode.ServiceUnavailable -> NetworkError.ServiceApiUnavailableError("Сервис недоступен")

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