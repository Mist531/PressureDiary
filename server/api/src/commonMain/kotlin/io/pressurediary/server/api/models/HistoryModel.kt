package io.pressurediary.server.api.models

import io.pressurediary.server.api.utils.LocalDateTimeSerializer
import io.pressurediary.server.api.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class HistoryModel(
    @Serializable(with = UUIDSerializer::class)
    val pressureRecordUUID: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTimeModified: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTimeRecord: LocalDateTime,
    val systolic: Int,
    val diastolic: Int,
    val pulse: Int,
    val note: String
)

@Serializable
data class RestoreHistoryModel(
    @Serializable(with = UUIDSerializer::class)
    val historyUUID: UUID
)

@Serializable
data class GetHistoryPressureRecordModel(
    @Serializable(with = UUIDSerializer::class)
    val pressureRecordUUID: UUID
)