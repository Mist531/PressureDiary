package com.backend.managers.usersManager.impl

import com.backend.database.tables.User
import com.backend.managers.usersManager.DeleteUserManager
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class DeleteUserManagerImpl : DeleteUserManager {
    override suspend operator fun invoke(param: UUID, request: Unit): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            User.findById(param).let { user ->
                if (user != null) {
                    user.delete()
                    HttpStatusCode.OK
                } else {
                    throw Exception("Пользователь не найден")
                }
            }
        }
}

