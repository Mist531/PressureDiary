package io.pressurediary.server.backend.managers.usersManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.PutUserRequestModel
import io.pressurediary.server.backend.database.tables.User
import io.pressurediary.server.backend.database.tables.UsersTable
import io.pressurediary.server.backend.managers.usersManager.PutUserManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

class PutUserManagerImpl : PutUserManager {
    override suspend fun request(
        param: UUID,
        request: PutUserRequestModel
    ): HttpStatusCode = requestTransaction {
        User.findById(param).let { existingUserRow ->
            if (existingUserRow != null) {
                UsersTable.update(
                    { UsersTable.id eq param }
                ) { update ->
                    request.firstName?.let { update[firstName] = request.firstName!! }
                    request.lastName?.let { update[lastName] = request.lastName }
                    request.dateOfBirth?.let { update[dateOfBirth] = request.dateOfBirth!! }
                    request.gender?.let { update[gender] = request.gender!! }
                    request.timeZone?.let { update[timeZone] = request.timeZone }
                }
                HttpStatusCode.OK
            } else throw Exception("Пользователь с таким UUID не найден")
        }
    }
}
