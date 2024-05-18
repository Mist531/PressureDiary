package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.LoginModel
import com.example.api.models.PostUserRequestModel
import com.example.api.models.PutUserRequestModel
import com.example.api.models.RefreshTokenModel
import com.example.api.models.TokensModel
import com.mist.common.modules.HTTP_CLIENT
import com.mist.common.modules.HTTP_CLIENT_AUTH
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.withContext
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface UserRepository {
    suspend fun login(model: LoginModel): Either<NetworkError, TokensModel>
    suspend fun postUser(model: PostUserRequestModel): Either<NetworkError, Unit>
    suspend fun putUser(model: PutUserRequestModel): Either<NetworkError, Unit>
    suspend fun deleteUser(): Either<NetworkError, Unit>
    suspend fun refreshToken(model: RefreshTokenModel): Either<NetworkError, TokensModel>
}

class UserRepositoryImpl : BaseRepository(), UserRepository {

    private val clientAuth: HttpClient by inject(named(HTTP_CLIENT_AUTH))

    private val client: HttpClient by inject(named(HTTP_CLIENT))

    override suspend fun login(model: LoginModel): Either<NetworkError, TokensModel> =
        withContext(repositoryScope.coroutineContext) {
            client.post(ApiRoutes.LOGIN) {
                setBody(model)
            }.handleResponse<TokensModel>()
        }

    override suspend fun postUser(model: PostUserRequestModel): Either<NetworkError, Unit> =
        withContext(repositoryScope.coroutineContext) {
            client.post(ApiRoutes.REGISTER_CREATE) {
                setBody(model)
            }.handleResponse<Unit>()
        }

    override suspend fun putUser(model: PutUserRequestModel): Either<NetworkError, Unit> =
        withContext(repositoryScope.coroutineContext) {
            clientAuth.put(ApiRoutes.User.EDIT) {
                setBody(model)
            }.handleResponse<Unit>()
        }

    override suspend fun deleteUser(): Either<NetworkError, Unit> =
        withContext(repositoryScope.coroutineContext) {
            clientAuth.delete(ApiRoutes.User.DELETE)
                .handleResponse<Unit>()
        }

    override suspend fun refreshToken(
        model: RefreshTokenModel
    ): Either<NetworkError, TokensModel> = withContext(repositoryScope.coroutineContext) {
        client.post(ApiRoutes.REFRESH_TOKEN) {
            setBody(model)
        }.handleResponse<TokensModel>()
    }
}