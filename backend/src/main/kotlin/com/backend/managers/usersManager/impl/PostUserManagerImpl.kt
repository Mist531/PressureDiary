package com.backend.managers.usersManager.impl

import com.backend.database.tables.UsersTable
import com.backend.managers.usersManager.PostUserManager
import com.backend.models.PostUserRequestModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PostUserManagerImpl : PostUserManager {
    override suspend operator fun invoke(param: Unit, request: PostUserRequestModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            UsersTable.select {
                UsersTable.email eq request.email
            }.firstOrNull().let { existingUser ->
                if (existingUser == null) {
                    UsersTable.insert {
                        it[email] = request.email
                        it[password] = request.password
                        it[firstName] = request.firstName
                        it[lastName] = request.lastName
                        it[dateOfBirth] = request.dateOfBirth
                        it[gender] = request.gender
                        it[timeZone] = request.timeZone
                    }
                    HttpStatusCode.Created
                } else {
                    throw Exception("Пользователь с таким email уже существует")
                }
            }
        }
}

