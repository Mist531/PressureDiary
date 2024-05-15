package com.example.api.models

import com.example.api.utils.LocalDateTimeSerializer
import com.example.api.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

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
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTimeRecord: LocalDateTime,
    val systolic: Int,
    val diastolic: Int,
    val pulse: Int,
    val note: String,
)

@Serializable
enum class DeviceType {
    ANDROID_WEAR,
    ANDROID,
    IOS
}
