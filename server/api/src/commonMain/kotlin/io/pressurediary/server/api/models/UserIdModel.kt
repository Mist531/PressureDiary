package io.pressurediary.server.api.models

import io.pressurediary.server.api.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserIdModel(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
)