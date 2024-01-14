package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.DeletePressureRecordModel
import com.example.api.models.GetPaginatedPressureRecordsModel
import com.example.api.models.PostPressureRecordModel
import com.example.api.models.PressureRecordModel
import com.example.api.models.PutPressureRecordModel
import com.mist.common.modules.HTTP_CLIENT_AUTH
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.withContext
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
}

class PressureRecordRepositoryImpl: PressureRecordRepository, BaseRepository() {
    private val client: HttpClient by inject(named(HTTP_CLIENT_AUTH))

    override suspend fun addPressureRecord(
        model: PostPressureRecordModel
    ): Either<NetworkError, Unit> = withContext(repositoryScope.coroutineContext) {
        client.post(ApiRoutes.PressureRecord.ADD) {
            setBody(model)
        }.handleResponse<Unit>()
    }

    override suspend fun deletePressureRecord(
        model: DeletePressureRecordModel
    ): Either<NetworkError, Unit> = withContext(repositoryScope.coroutineContext) {
        client.delete(ApiRoutes.PressureRecord.DELETE) {
            setBody(model)
        }.handleResponse<Unit>()
    }

    override suspend fun editPressureRecord(
        model: PutPressureRecordModel
    ): Either<NetworkError, Unit> = withContext(repositoryScope.coroutineContext) {
        client.put(ApiRoutes.PressureRecord.EDIT) {
            setBody(model)
        }.handleResponse<Unit>()
    }

    override suspend fun getPaginatedPressureRecords(
        model: GetPaginatedPressureRecordsModel
    ): Either<NetworkError, List<PressureRecordModel>> =
        withContext(repositoryScope.coroutineContext) {
            client.get(ApiRoutes.PressureRecord.GET_PAGINATED) {
                setBody(model)
            }.handleResponse<List<PressureRecordModel>>()
        }

}