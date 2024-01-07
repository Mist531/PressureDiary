package com.backend.models

import com.backend.database.tables.Gender
import com.backend.utils.LocalDateSerializer
import com.backend.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*

@Serializable
data class PostUserRequestModel(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String? = null,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfBirth: LocalDate,
    val gender: Gender = Gender.O,
    val timeZone: String?
)

@Serializable
data class PutUserRequestModel(
    val firstName: String? = null,
    val lastName: String? = null,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfBirth: LocalDate? = null,
    val gender: Gender? = null,
    val timeZone: String? = null
)
