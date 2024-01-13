package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.*
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.withContext

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

class PressureRecordRepositoryImpl(
    private val client: HttpClient
) : PressureRecordRepository, BaseRepository() {
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
            client.put(ApiRoutes.PressureRecord.GET_PAGINATED) {
                setBody(model)
            }.handleResponse<List<PressureRecordModel>>()
        }

}