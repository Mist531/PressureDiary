package io.pressurediary.android.common.data.repository

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.pressurediary.android.common.modules.HTTP_CLIENT
import io.pressurediary.android.common.modules.HTTP_CLIENT_AUTH
import io.pressurediary.android.common.utils.BaseRepository
import io.pressurediary.android.common.utils.errorflow.NetworkError
import io.pressurediary.server.api.ApiRoutes
import io.pressurediary.server.api.models.LoginModel
import io.pressurediary.server.api.models.PostUserRequestModel
import io.pressurediary.server.api.models.PutUserRequestModel
import io.pressurediary.server.api.models.RefreshTokenModel
import io.pressurediary.server.api.models.TokensModel
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface UserRepository {
    suspend fun login(
        model: LoginModel
    ): Either<NetworkError, TokensModel>

    suspend fun postUser(
        model: PostUserRequestModel
    ): Either<NetworkError, Unit>

    suspend fun putUser(
        model: PutUserRequestModel
    ): Either<NetworkError, Unit>

    suspend fun deleteUser(): Either<NetworkError, Unit>

    suspend fun refreshToken(
        model: RefreshTokenModel
    ): Either<NetworkError, TokensModel>
}

class UserRepositoryImpl() : BaseRepository(), UserRepository {
    private val clientAuth: HttpClient by inject(named(HTTP_CLIENT_AUTH))

    private val client: HttpClient by inject(named(HTTP_CLIENT))

    override suspend fun login(
        model: LoginModel
    ): Either<NetworkError, TokensModel> = repositoryContext {
        client.post(ApiRoutes.LOGIN) {
            setBody(model)
        }.handleResponse<TokensModel>()
    }

    override suspend fun postUser(
        model: PostUserRequestModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.post(ApiRoutes.REGISTER_CREATE) {
            setBody(model)
        }.handleResponse<Unit>()
    }

    override suspend fun putUser(
        model: PutUserRequestModel
    ): Either<NetworkError, Unit> = repositoryContext {
        clientAuth.put(ApiRoutes.User.EDIT) {
            setBody(model)
        }.handleResponse<Unit>()
    }

    override suspend fun deleteUser(
    ): Either<NetworkError, Unit> = repositoryContext {
        clientAuth.delete(ApiRoutes.User.DELETE)
            .handleResponse<Unit>()
    }

    override suspend fun refreshToken(
        model: RefreshTokenModel
    ): Either<NetworkError, TokensModel> = repositoryContext {
        client.post(ApiRoutes.REFRESH_TOKEN) {
            setBody(model)
        }.handleResponse<TokensModel>()
    }
}