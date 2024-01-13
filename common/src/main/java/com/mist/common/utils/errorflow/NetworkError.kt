package com.mist.common.utils.errorflow

sealed class NetworkError(val message: String) {
    class JsonParseError(message: String) : NetworkError(message)
    class AuthenticationError(message: String) : NetworkError(message)
    class RequestError(
        code: Int,
        message: String
    ) : NetworkError("code: $code, message: $message")

    class UnknownError(message: String) : NetworkError(message)
}