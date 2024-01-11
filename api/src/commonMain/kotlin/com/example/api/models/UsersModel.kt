package com.example.api.models

import com.example.api.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PostUserRequestModel(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String? = null,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfBirth: LocalDate,
    val gender: Gender = Gender.O,
    val timeZone: String? = null
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

@Serializable
enum class Gender {
    M,
    F,
    O
}