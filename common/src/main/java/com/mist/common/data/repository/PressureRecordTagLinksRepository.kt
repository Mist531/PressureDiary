package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.AddPressureRecordTagLinkModel
import com.example.api.models.DeletePressureRecordTagLinkByRecordModel
import com.example.api.models.DeletePressureRecordTagLinkByTagModel
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.withContext

interface PressureRecordTagLinksRepository {
    suspend fun addPressureRecordTagLink(
        addPressureRecordTagLinkModel: AddPressureRecordTagLinkModel
    ): Either<NetworkError, Unit>

    suspend fun deletePressureRecordTagLinkByRecord(
        deletePressureRecordTagLinkByRecordModel: DeletePressureRecordTagLinkByRecordModel
    ): Either<NetworkError, Unit>

    suspend fun deletePressureRecordTagLinkByTag(
        deletePressureRecordTagLinkByTagModel: DeletePressureRecordTagLinkByTagModel
    ): Either<NetworkError, Unit>
}

class PressureRecordTagLinksRepositoryImpl(
    private val client: HttpClient
) : BaseRepository(), PressureRecordTagLinksRepository {
    override suspend fun addPressureRecordTagLink(
        addPressureRecordTagLinkModel: AddPressureRecordTagLinkModel
    ): Either<NetworkError, Unit> = withContext(repositoryScope.coroutineContext) {
        client.post(ApiRoutes.PressureRecordTagLinks.ADD) {
            setBody(addPressureRecordTagLinkModel)
        }.handleResponse<Unit>()
    }

    override suspend fun deletePressureRecordTagLinkByRecord(
        deletePressureRecordTagLinkByRecordModel: DeletePressureRecordTagLinkByRecordModel
    ): Either<NetworkError, Unit> = withContext(repositoryScope.coroutineContext) {
        client.post(ApiRoutes.PressureRecordTagLinks.DELETE_BY_RECORD) {
            setBody(deletePressureRecordTagLinkByRecordModel)
        }.handleResponse<Unit>()
    }

    override suspend fun deletePressureRecordTagLinkByTag(
        deletePressureRecordTagLinkByTagModel: DeletePressureRecordTagLinkByTagModel
    ): Either<NetworkError, Unit> = withContext(repositoryScope.coroutineContext) {
        client.post(ApiRoutes.PressureRecordTagLinks.DELETE_BY_TAG) {
            setBody(deletePressureRecordTagLinkByTagModel)
        }.handleResponse<Unit>()
    }
}