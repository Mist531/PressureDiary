package io.pressurediary.server.backend.managersImpl

import io.pressurediary.server.backend.authorization.AuthUtil
import io.pressurediary.server.backend.managers.usersManager.DeleteUserManager
import io.pressurediary.server.backend.managers.usersManager.LoginUserManager
import io.pressurediary.server.backend.managers.usersManager.PostUserManager
import io.pressurediary.server.backend.managers.usersManager.PutUserManager
import io.pressurediary.server.backend.managers.usersManager.RefreshTokensManager
import io.pressurediary.server.api.models.LoginModel
import io.pressurediary.server.api.models.PostUserRequestModel
import io.pressurediary.server.api.models.PutUserRequestModel
import io.pressurediary.server.api.models.RefreshTokenModel
import io.pressurediary.server.api.models.TokensModel
import io.ktor.http.HttpStatusCode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

interface UserManager {
    suspend fun login(model: LoginModel): TokensModel
    suspend fun postUser(model: PostUserRequestModel): HttpStatusCode
    suspend fun putUser(userId: UUID, model: PutUserRequestModel): HttpStatusCode
    suspend fun deleteUser(userId: UUID): HttpStatusCode
    suspend fun refreshToken(model: RefreshTokenModel): TokensModel
}

class UserManagerImpl : UserManager, KoinComponent {
    override suspend fun login(model: LoginModel): TokensModel {
        val manager: LoginUserManager by inject()
        return runCatching {
            manager.request(Unit, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            TokensModel(
                access = AuthUtil.buildAccess(it.userId.toString()),
                refresh = AuthUtil.buildRefresh(it.userId.toString()),
            )
        }
    }

    override suspend fun postUser(model: PostUserRequestModel): HttpStatusCode {
        val manager: PostUserManager by inject()
        return runCatching {
            manager.request(Unit, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun putUser(userId: UUID, model: PutUserRequestModel): HttpStatusCode {
        val manager: PutUserManager by inject()
        return runCatching {
            manager.request(userId, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun deleteUser(userId: UUID): HttpStatusCode {
        val manager: DeleteUserManager by inject()
        return runCatching {
            manager.request(userId, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun refreshToken(model: RefreshTokenModel): TokensModel {
        val manager: RefreshTokensManager by inject()
        return runCatching {
            manager.request(Unit, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}
