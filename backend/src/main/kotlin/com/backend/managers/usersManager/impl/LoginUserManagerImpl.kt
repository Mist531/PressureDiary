package com.backend.managers.usersManager.impl

import at.favre.lib.crypto.bcrypt.BCrypt
import com.backend.database.tables.User
import com.backend.database.tables.UsersTable
import com.backend.managers.usersManager.LoginUserManager
import com.example.api.models.LoginModel
import com.example.api.models.UserIdModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class LoginUserManagerImpl : LoginUserManager {
    override suspend fun invoke(param: Unit, request: LoginModel): UserIdModel =
        newSuspendedTransaction(Dispatchers.IO) {
            User.find {
                UsersTable.email eq request.email
            }.firstOrNull()?.let { findUser ->
                BCrypt.verifyer().verify(
                    request.password.toCharArray(),
                    findUser.password
                ).let {
                    if (it.verified) {
                        return@newSuspendedTransaction UserIdModel(findUser.id.value)
                    } else {
                        throw Exception("Неверный пароль")
                    }
                }
            } ?: throw Exception("Пользователь не найден")
        }
}