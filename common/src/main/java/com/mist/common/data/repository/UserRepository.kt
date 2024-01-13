package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.LoginModel
import com.example.api.models.PostUserRequestModel
import com.example.api.models.PutUserRequestModel
import com.example.api.models.TokensModel
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.withContext

interface UserRepository {
    suspend fun login(model: LoginModel): Either<NetworkError, TokensModel>
    suspend fun postUser(model: PostUserRequestModel): Either<NetworkError, Unit>
    suspend fun putUser(model: PutUserRequestModel): Either<NetworkError, Unit>
    suspend fun deleteUser(): Either<NetworkError, Unit>
}

class UserRepositoryImpl(
    private val client: HttpClient
) : BaseRepository(), UserRepository {

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
            client.put(ApiRoutes.User.EDIT) {
                setBody(model)
            }.handleResponse<Unit>()
        }

    override suspend fun deleteUser(): Either<NetworkError, Unit> =
        withContext(repositoryScope.coroutineContext) {
            client.delete(ApiRoutes.User.DELETE)
                .handleResponse<Unit>()
        }
}