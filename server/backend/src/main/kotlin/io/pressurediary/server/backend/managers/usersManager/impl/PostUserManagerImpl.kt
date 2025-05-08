package io.pressurediary.server.backend.managers.usersManager.impl

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.PostUserRequestModel
import io.pressurediary.server.backend.database.tables.UsersTable
import io.pressurediary.server.backend.managers.usersManager.PostUserManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostUserManagerImpl : PostUserManager {
    override suspend fun request(
        param: Unit,
        request: PostUserRequestModel
    ): HttpStatusCode = requestTransaction {
        UsersTable.selectAll().where {
            UsersTable.email eq request.email
        }.firstOrNull().let { existingUser ->
            if (existingUser == null) {
                UsersTable.insert {
                    it[email] = request.email
                    it[password] = BCrypt.withDefaults().hash(12, request.password.toCharArray())
                    it[firstName] = request.firstName
                    it[lastName] = request.lastName
                    it[dateOfBirth] = request.dateOfBirth
                    it[gender] = request.gender
                    it[timeZone] = request.timeZone
                }
                HttpStatusCode.Created
            } else throw Exception("Пользователь с таким email уже существует")
        }
    }
}

