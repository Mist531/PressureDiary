package io.pressurediary.server.backend.managers.usersManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.backend.database.tables.User
import io.pressurediary.server.backend.managers.usersManager.DeleteUserManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class DeleteUserManagerImpl : DeleteUserManager {
    override suspend fun request(
        param: UUID,
        request: Unit
    ): HttpStatusCode = requestTransaction {
        User.findById(param).let { user ->
            if (user != null) {
                user.delete()
                HttpStatusCode.OK
            } else throw Exception("Пользователь не найден")
        }
    }
}

