package io.pressurediary.server.api.models

import io.pressurediary.server.api.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

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