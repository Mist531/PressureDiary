package io.pressurediary.android.common.data.repository

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.pressurediary.android.common.modules.HTTP_CLIENT
import io.pressurediary.android.common.utils.BaseRepository
import io.pressurediary.android.common.utils.errorflow.NetworkError
import io.pressurediary.server.api.ApiRoutes
import io.pressurediary.server.api.models.AddTagModel
import io.pressurediary.server.api.models.DeleteUserTagModel
import io.pressurediary.server.api.models.TagModel
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

class TagsRepositoryImpl() : BaseRepository(), TagsRepository {
    private val client: HttpClient by inject(named(HTTP_CLIENT))

    override suspend fun getUserTagsList(
    ): Either<NetworkError, List<TagModel>> = repositoryContext {
        client.get(ApiRoutes.Tags.GET)
            .handleResponse<List<TagModel>>()
    }

    override suspend fun addTagForUser(
        addTagModel: AddTagModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.get(ApiRoutes.Tags.ADD) {
            setBody(addTagModel)
        }.handleResponse<Unit>()
    }

    override suspend fun deleteUserTag(
        deleteUserTagModel: DeleteUserTagModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.delete(ApiRoutes.Tags.DELETE) {
            setBody(deleteUserTagModel)
        }.handleResponse<Unit>()
    }

    override suspend fun deleteAllTagsForUser(
    ): Either<NetworkError, Unit> = repositoryContext {
        client.delete(ApiRoutes.Tags.DELETE_ALL)
            .handleResponse<Unit>()
    }
}