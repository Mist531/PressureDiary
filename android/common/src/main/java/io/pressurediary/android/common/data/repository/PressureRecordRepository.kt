package io.pressurediary.android.common.data.repository

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.pressurediary.android.common.modules.HTTP_CLIENT
import io.pressurediary.android.common.utils.BaseRepository
import io.pressurediary.android.common.utils.errorflow.NetworkError
import io.pressurediary.server.api.ApiRoutes
import io.pressurediary.server.api.models.DeletePressureRecordModel
import io.pressurediary.server.api.models.GetAllPressureRecordsModel
import io.pressurediary.server.api.models.GetPaginatedPressureRecordsModel
import io.pressurediary.server.api.models.PostPressureRecordModel
import io.pressurediary.server.api.models.PressureRecordModel
import io.pressurediary.server.api.models.PutPressureRecordModel
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface PressureRecordRepository {
    suspend fun addPressureRecord(
        model: PostPressureRecordModel
    ): Either<NetworkError, Unit>

    suspend fun deletePressureRecord(
        model: DeletePressureRecordModel
    ): Either<NetworkError, Unit>

    suspend fun editPressureRecord(
        model: PutPressureRecordModel
    ): Either<NetworkError, Unit>

    suspend fun getPaginatedPressureRecords(
        model: GetPaginatedPressureRecordsModel
    ): Either<NetworkError, List<PressureRecordModel>>

    suspend fun getAllPressureRecords(
        model: GetAllPressureRecordsModel
    ): Either<NetworkError, List<PressureRecordModel>>
}

class PressureRecordRepositoryImpl() : PressureRecordRepository, BaseRepository() {
    private val client: HttpClient by inject(named(HTTP_CLIENT))

    override suspend fun addPressureRecord(
        model: PostPressureRecordModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.post(ApiRoutes.PressureRecord.ADD) {
            setBody(model)
        }.handleResponse<Unit>()
    }

    override suspend fun deletePressureRecord(
        model: DeletePressureRecordModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.delete(ApiRoutes.PressureRecord.DELETE) {
            setBody(model)
        }.handleResponse<Unit>()
    }

    override suspend fun editPressureRecord(
        model: PutPressureRecordModel
    ): Either<NetworkError, Unit> = repositoryContext {
        client.put(ApiRoutes.PressureRecord.EDIT) {
            setBody(model)
        }.handleResponse<Unit>()
    }

    override suspend fun getPaginatedPressureRecords(
        model: GetPaginatedPressureRecordsModel
    ): Either<NetworkError, List<PressureRecordModel>> = repositoryContext {
        client.get(ApiRoutes.PressureRecord.GET_PAGINATED) {
            setBody(model)
        }.handleResponse<List<PressureRecordModel>>()
    }

    override suspend fun getAllPressureRecords(
        model: GetAllPressureRecordsModel
    ): Either<NetworkError, List<PressureRecordModel>> = repositoryContext {
        client.get(ApiRoutes.PressureRecord.GET_ALL) {
            setBody(model)
        }.handleResponse<List<PressureRecordModel>>()
    }
}