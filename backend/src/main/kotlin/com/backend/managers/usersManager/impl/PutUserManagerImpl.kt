package com.backend.managers.usersManager.impl

import com.backend.database.tables.User
import com.backend.database.tables.UsersTable
import com.backend.managers.usersManager.PutUserManager
import com.example.api.models.PutUserRequestModel
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

class PutUserManagerImpl : PutUserManager {
    override suspend operator fun invoke(param: UUID, request: PutUserRequestModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            User.findById(param).let { existingUserRow ->
                if (existingUserRow != null) {
                    UsersTable.update({ UsersTable.id eq param }) { update ->
                        request.firstName?.let { update[firstName] = request.firstName!! }
                        request.lastName?.let { update[lastName] = request.lastName }
                        request.dateOfBirth?.let { update[dateOfBirth] = request.dateOfBirth!! }
                        request.gender?.let { update[gender] = request.gender!! }
                        request.timeZone?.let { update[timeZone] = request.timeZone }
                    }
                    HttpStatusCode.OK
                } else {
                    throw Exception("Пользователь с таким UUID не найден")
                }
            }
        }
}
