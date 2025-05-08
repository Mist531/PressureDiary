package io.pressurediary.server.backend.managers.usersManager.impl

import io.pressurediary.server.api.models.RefreshTokenModel
import io.pressurediary.server.api.models.TokensModel
import io.pressurediary.server.backend.authorization.AuthUtil
import io.pressurediary.server.backend.database.tables.User
import io.pressurediary.server.backend.database.tables.UsersTable
import io.pressurediary.server.backend.managers.usersManager.RefreshTokensManager

class RefreshTokensManagerImpl : RefreshTokensManager {
    override suspend fun request(
        param: Unit,
        request: RefreshTokenModel
    ): TokensModel = requestTransaction {
        AuthUtil.getUUIDFromRefreshToken(request.refresh).let { userId ->
            User.find {
                UsersTable.id eq userId
            }.firstOrNull()?.let { findUser ->
                return@requestTransaction TokensModel(
                    access = AuthUtil.buildAccess(findUser.id.value.toString()),
                    refresh = AuthUtil.buildRefresh(findUser.id.value.toString()),
                )
            } ?: throw Exception("Пользователь не найден")
        }
    }
}