package com.backend.models

import com.backend.database.tables.DeviceType
import com.backend.utils.LocalDateTimeSerializer
import com.backend.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class PostPressureRecordModel(
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTimeRecord: LocalDateTime,
    val systolic: Int,
    val diastolic: Int,
    val pulse: Int,
    val note: String = "",
    val deviceType: DeviceType
)

@Serializable
data class DeletePressureRecordModel(
    @Serializable(with = UUIDSerializer::class)
    val pressureRecordUUID: UUID
)

@Serializable
data class PutPressureRecordModel(
    @Serializable(with = UUIDSerializer::class)
    val pressureRecordUUID: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTimeRecord: LocalDateTime?,
    val systolic: Int?,
    val diastolic: Int?,
    val pulse: Int?,
    val note: String?,
)

@Serializable
data class GetPaginatedPressureRecordsModel(
    @Serializable(with = UUIDSerializer::class)
    val userUUID: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val fromDateTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val toDateTime: LocalDateTime,
    val page: Int,
    val pageSize: Int
)

@Serializable
data class PressureRecordModel(
    @Serializable(with = UUIDSerializer::class)
    val pressureRecordUUID: UUID,
    @Serializable(with = UUIDSerializer::class)
    val userUUID: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTimeRecord: LocalDateTime,
    val systolic: Int,
    val diastolic: Int,
    val pulse: Int,
    val note: String,
    val deviceType: DeviceType
)
