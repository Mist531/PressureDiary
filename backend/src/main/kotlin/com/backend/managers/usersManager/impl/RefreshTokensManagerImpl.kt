package com.backend.managers.usersManager.impl

import com.backend.authorization.AuthUtil
import com.backend.database.tables.User
import com.backend.database.tables.UsersTable
import com.backend.managers.usersManager.RefreshTokensManager
import com.example.api.models.RefreshTokenModel
import com.example.api.models.TokensModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class RefreshTokensManagerImpl : RefreshTokensManager {
    override suspend operator fun invoke(param: Unit, request: RefreshTokenModel): TokensModel =
        newSuspendedTransaction(Dispatchers.IO) {
            AuthUtil.getUUIDFromRefreshToken(request.refresh).let { userId ->
                User.find {
                    UsersTable.id eq userId
                }.firstOrNull()?.let { findUser ->
                    return@newSuspendedTransaction TokensModel(
                        access = AuthUtil.buildAccess(findUser.id.value.toString()),
                        refresh = AuthUtil.buildRefresh(findUser.id.value.toString()),
                    )
                } ?: throw Exception("Пользователь не найден")
            }
        }
}