package com.example.api.models

import com.example.api.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserIdModel(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
)