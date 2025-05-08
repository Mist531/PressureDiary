package io.pressurediary.server.backend.managers.usersManager.impl

import at.favre.lib.crypto.bcrypt.BCrypt
import io.pressurediary.server.api.models.LoginModel
import io.pressurediary.server.api.models.UserIdModel
import io.pressurediary.server.backend.database.tables.User
import io.pressurediary.server.backend.database.tables.UsersTable
import io.pressurediary.server.backend.managers.usersManager.LoginUserManager

class LoginUserManagerImpl : LoginUserManager {
    override suspend fun request(
        param: Unit,
        request: LoginModel
    ): UserIdModel = requestTransaction {
        User.find {
            UsersTable.email eq request.email
        }.firstOrNull()?.let { findUser ->
            BCrypt.verifyer().verify(
                request.password.toCharArray(),
                findUser.password
            ).let {
                if (it.verified) {
                    return@requestTransaction UserIdModel(findUser.id.value)
                } else throw Exception("Неверный пароль")
            }
        } ?: throw Exception("Пользователь не найден")
    }
}