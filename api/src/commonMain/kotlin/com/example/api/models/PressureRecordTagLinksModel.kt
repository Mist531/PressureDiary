package com.example.api.models

import com.example.api.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class DeletePressureRecordTagLinkByRecordModel(
    @Serializable(with = UUIDSerializer::class)
    val pressureRecordUUID: UUID
)

@Serializable
data class DeletePressureRecordTagLinkByTagModel(
    @Serializable(with = UUIDSerializer::class)
    val tagUUID: UUID
)

@Serializable
data class AddPressureRecordTagLinkModel(
    @Serializable(with = UUIDSerializer::class)
    val pressureRecordUUID: UUID,
    @Serializable(with = UUIDSerializer::class)
    val tagUUID: UUID
)
