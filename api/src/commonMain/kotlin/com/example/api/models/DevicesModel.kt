package com.example.api.models

import com.example.api.utils.LocalDateSerializer
import com.example.api.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

@Serializable
data class PostDeviceForUserModel(
    val deviceType: DeviceType,
    @Serializable(with = LocalDateSerializer::class)
    val lastSyncDate: LocalDate,
)

@Serializable
data class DeleteUserDeviceModel(
    @Serializable(with = UUIDSerializer::class)
    val deviceUUID: UUID
)

@Serializable
data class DeviceModel(
    @Serializable(with = UUIDSerializer::class)
    val deviceUUID: UUID,
    val deviceType: DeviceType,
    @Serializable(with = LocalDateSerializer::class)
    val lastSyncDate: LocalDate
)