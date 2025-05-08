package io.pressurediary.server.api.models

import io.pressurediary.server.api.utils.LocalDateSerializer
import io.pressurediary.server.api.utils.LocalTimeSerializer
import io.pressurediary.server.api.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Serializable
data class NotificationModel(
    val message: String,
    @Serializable(with = LocalTimeSerializer::class)
    val timeToSend: LocalTime,
    @Serializable(with = LocalDateSerializer::class)
    val lastSentDate: LocalDate?
)

@Serializable
data class UpdateNotificationModel(
    @Serializable(with = UUIDSerializer::class)
    val notificationUUID: UUID,
    val message: String? = null,
    @Serializable(with = LocalTimeSerializer::class)
    val timeToSend: LocalTime? = null,
    @Serializable(with = LocalDateSerializer::class)
    val lastSentDate: LocalDate? = null
)