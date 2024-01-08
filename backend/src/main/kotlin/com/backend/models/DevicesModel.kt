package com.backend.models

import com.backend.database.tables.DeviceType
import com.backend.utils.LocalDateSerializer
import com.backend.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*

@Serializable
data class PostDeviceForUserModel(
    val deviceType: DeviceType,
    @Serializable(with = LocalDateSerializer::class)
    val lastSyncDate: LocalDate
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