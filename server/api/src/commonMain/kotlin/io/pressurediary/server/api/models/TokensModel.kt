package io.pressurediary.server.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TokensModel(
    val access: String,
    val refresh: String
)

@Serializable
data class RefreshTokenModel(
    val refresh: String
)