package com.backend.authorization.models

import com.backend.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserIdModel(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
)