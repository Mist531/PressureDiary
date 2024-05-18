package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.AddTagModel
import com.example.api.models.DeleteUserTagModel
import com.example.api.models.TagModel
import com.mist.common.modules.HTTP_CLIENT_AUTH
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import kotlinx.coroutines.withContext
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface TagsRepository {
    suspend fun getUserTagsList(): Either<NetworkError, List<TagModel>>
    suspend fun addTagForUser(
        addTagModel: AddTagModel
    ): Either<NetworkError, Unit>

    suspend fun deleteUserTag(
        deleteUserTagModel: DeleteUserTagModel
    ): Either<NetworkError, Unit>

    suspend fun deleteAllTagsForUser(): Either<NetworkError, Unit>
}

class TagsRepositoryImpl : BaseRepository(), TagsRepository {
    private val client: HttpClient by inject(named(HTTP_CLIENT_AUTH))

    override suspend fun getUserTagsList(): Either<NetworkError, List<TagModel>> =
        withContext(repositoryScope.coroutineContext) {
            client.get(ApiRoutes.Tags.GET)
                .handleResponse<List<TagModel>>()
        }

    override suspend fun addTagForUser(addTagModel: AddTagModel): Either<NetworkError, Unit> =
        withContext(repositoryScope.coroutineContext) {
            client.get(ApiRoutes.Tags.ADD) {
                setBody(addTagModel)
            }.handleResponse<Unit>()
        }

    override suspend fun deleteUserTag(deleteUserTagModel: DeleteUserTagModel): Either<NetworkError, Unit> =
        withContext(repositoryScope.coroutineContext) {
            client.delete(ApiRoutes.Tags.DELETE) {
                setBody(deleteUserTagModel)
            }.handleResponse<Unit>()
        }

    override suspend fun deleteAllTagsForUser(): Either<NetworkError, Unit> =
        withContext(repositoryScope.coroutineContext) {
            client.delete(ApiRoutes.Tags.DELETE_ALL)
                .handleResponse<Unit>()
        }
}