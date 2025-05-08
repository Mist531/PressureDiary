package io.pressurediary.server.backend.database.tables

import io.pressurediary.server.api.models.Gender
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentDate
import org.jetbrains.exposed.sql.javatime.date
import java.util.UUID

object UsersTable : UUIDTable("Users", "userUUID") {
    val email = varchar("email", 30)
    val password = binary("password", 120)
    val firstName = varchar("firstName", 30)
    val lastName = varchar("lastName", 30).nullable().default(null)
    val dateOfBirth = date("dateOfBirth")
    val gender = enumeration("gender", Gender::class).default(Gender.O)
    val dateRegistered = date("dateRegistered").defaultExpression(CurrentDate)
    val timeZone = varchar("timeZone", 255).nullable().default(null)
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