package com.backend.models

import com.backend.utils.UUIDSerializer
import java.util.*
import kotlinx.serialization.Serializable

@Serializable
data class AddTagModel(
    val name: String
)

@Serializable
data class TagModel(
    @Serializable(with = UUIDSerializer::class)
    val tagId: UUID? = null,
    val name: String
)

@Serializable
data class DeleteUserTagModel(
    @Serializable(with = UUIDSerializer::class)
    val tagId: UUID
)