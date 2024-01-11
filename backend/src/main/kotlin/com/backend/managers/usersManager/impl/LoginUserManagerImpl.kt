package com.backend.managers.usersManager.impl

import com.example.api.models.LoginModel
import com.example.api.models.UserIdModel
import com.backend.database.tables.User
import com.backend.database.tables.UsersTable
import com.backend.managers.usersManager.LoginUserManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class LoginUserManagerImpl : LoginUserManager {
    override suspend fun invoke(param: Unit, request: LoginModel): UserIdModel =
        newSuspendedTransaction(Dispatchers.IO) {
            (User.find {
                UsersTable.email eq request.login and (UsersTable.password eq request.password)
            }.firstOrNull() ?: throw Exception("Пользователь не найден")
                    ).let {
                    return@newSuspendedTransaction UserIdModel(it.id.value)
                }
        }
}