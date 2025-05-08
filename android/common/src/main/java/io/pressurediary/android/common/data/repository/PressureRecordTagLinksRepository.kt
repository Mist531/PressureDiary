package io.pressurediary.android.common.data.repository

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.pressurediary.android.common.modules.HTTP_CLIENT
import io.pressurediary.android.common.utils.BaseRepository
import io.pressurediary.android.common.utils.errorflow.NetworkError
import io.pressurediary.server.api.ApiRoutes
import io.pressurediary.server.api.models.AddPressureRecordTagLinkModel
import io.pressurediary.server.api.models.DeletePressureRecordTagLinkByRecordModel
import io.pressurediary.server.api.models.DeletePressureRecordTagLinkByTagModel
import org.koin.core.component.inject
import org.koin.core.qualifier.named

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

class PressureRecordTagLinksRepositoryImpl() : BaseRepository(), PressureRecordTagLinksRepository {
    private val client: HttpClient by inject(named(HTTP_CLIENT))

    override suspend fun addPressureRecordTagLink(
        addPressureRecordTagLinkModel: AddPressureRecordTagLinkModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.post(ApiRoutes.PressureRecordTagLinks.ADD) {
            setBody(addPressureRecordTagLinkModel)
        }.handleResponse<Unit>()
    }

    override suspend fun deletePressureRecordTagLinkByRecord(
        deletePressureRecordTagLinkByRecordModel: DeletePressureRecordTagLinkByRecordModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.post(ApiRoutes.PressureRecordTagLinks.DELETE_BY_RECORD) {
            setBody(deletePressureRecordTagLinkByRecordModel)
        }.handleResponse<Unit>()
    }

    override suspend fun deletePressureRecordTagLinkByTag(
        deletePressureRecordTagLinkByTagModel: DeletePressureRecordTagLinkByTagModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.post(ApiRoutes.PressureRecordTagLinks.DELETE_BY_TAG) {
            setBody(deletePressureRecordTagLinkByTagModel)
        }.handleResponse<Unit>()
    }
}