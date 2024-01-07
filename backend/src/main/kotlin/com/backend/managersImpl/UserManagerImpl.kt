package com.backend.managersImpl

import com.backend.authorization.AuthUtil
import com.backend.authorization.models.LoginModel
import com.backend.authorization.models.TokensModel
import com.backend.managers.usersManager.DeleteUserManager
import com.backend.managers.usersManager.LoginUserManager
import com.backend.managers.usersManager.PostUserManager
import com.backend.managers.usersManager.PutUserManager
import com.backend.models.PostUserRequestModel
import com.backend.models.PutUserRequestModel
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

interface UserManager {
    suspend fun login(model: LoginModel): TokensModel
    suspend fun postUser(model: PostUserRequestModel): HttpStatusCode
    suspend fun putUser(id: UUID, model: PutUserRequestModel): HttpStatusCode
    suspend fun deleteUser(id: UUID): HttpStatusCode
}

class UserManagerImpl : UserManager, KoinComponent {
    override suspend fun login(model: LoginModel): TokensModel {
        val manager: LoginUserManager by inject()
        return runCatching {
            manager.invoke(Unit, model)
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
            manager.invoke(Unit, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun putUser(id: UUID, model: PutUserRequestModel): HttpStatusCode {
        val manager: PutUserManager by inject()
        return runCatching {
            manager.invoke(id, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun deleteUser(id: UUID): HttpStatusCode {
        val manager: DeleteUserManager by inject()
        return runCatching {
            manager.invoke(id, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }
}
