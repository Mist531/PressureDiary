package com.backend.database.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate
import java.util.*

object UsersTable : UUIDTable("Users", "userUUID") {
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val firstName = varchar("firstName", 255)
    val lastName = varchar("lastName", 255).nullable().default(null)
    val dateOfBirth = date("dateOfBirth")
    val gender = enumerationByName("gender", 10, Gender::class).default(Gender.O)
    val dateRegistered = date("dateRegistered").default(LocalDate.now())
    val timeZone = varchar("timeZone", 255).nullable()
}

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(UsersTable)

    var email by UsersTable.email
    var password by UsersTable.password
    var firstName by UsersTable.firstName
    var lastName by UsersTable.lastName
    var dateOfBirth by UsersTable.dateOfBirth
    var gender by UsersTable.gender
    var dateRegistered by UsersTable.dateRegistered
    var timeZone by UsersTable.timeZone
}

@Serializable
enum class Gender {
    M,
    F,
    O
}
