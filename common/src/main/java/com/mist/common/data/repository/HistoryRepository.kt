package com.mist.common.data.repository

import arrow.core.Either
import com.example.api.ApiRoutes
import com.example.api.models.GetHistoryPressureRecordModel
import com.example.api.models.HistoryModel
import com.example.api.models.RestoreHistoryModel
import com.mist.common.utils.BaseRepository
import com.mist.common.utils.errorflow.NetworkError
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.withContext

interface HistoryRepository {
    suspend fun getLastThreeHistoryForRecord(getHistoryPressureRecordModel: GetHistoryPressureRecordModel): Either<NetworkError, List<HistoryModel>>
    suspend fun restoreRecordFromHistory(model: RestoreHistoryModel): Either<NetworkError, HistoryModel>
}

class HistoryRepositoryImpl(
    private val client: HttpClient
) : BaseRepository(), HistoryRepository {
    override suspend fun getLastThreeHistoryForRecord(
        getHistoryPressureRecordModel: GetHistoryPressureRecordModel
    ): Either<NetworkError, List<HistoryModel>> =
        withContext(repositoryScope.coroutineContext) {
            client.get(ApiRoutes.History.GET_HISTORY) {
                setBody(getHistoryPressureRecordModel)
            }.handleResponse<List<HistoryModel>>()
        }

    override suspend fun restoreRecordFromHistory(model: RestoreHistoryModel): Either<NetworkError, HistoryModel> =
        withContext(repositoryScope.coroutineContext) {
            client.post(ApiRoutes.History.RESTORE_FROM_HISTORY) {
                setBody(model)
            }.handleResponse<HistoryModel>()
        }
}