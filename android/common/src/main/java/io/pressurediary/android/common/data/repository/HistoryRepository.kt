package io.pressurediary.android.common.data.repository

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.pressurediary.android.common.modules.HTTP_CLIENT
import io.pressurediary.android.common.utils.BaseRepository
import io.pressurediary.android.common.utils.errorflow.NetworkError
import io.pressurediary.server.api.ApiRoutes
import io.pressurediary.server.api.models.GetHistoryPressureRecordModel
import io.pressurediary.server.api.models.HistoryModel
import io.pressurediary.server.api.models.RestoreHistoryModel
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface HistoryRepository {
    suspend fun getLastThreeHistoryForRecord(
        getHistoryPressureRecordModel: GetHistoryPressureRecordModel
    ): Either<NetworkError, List<HistoryModel>>

    suspend fun restoreRecordFromHistory(
        model: RestoreHistoryModel
    ): Either<NetworkError, HistoryModel>
}

class HistoryRepositoryImpl() : BaseRepository(), HistoryRepository {
    private val client: HttpClient by inject(named(HTTP_CLIENT))

    override suspend fun getLastThreeHistoryForRecord(
        getHistoryPressureRecordModel: GetHistoryPressureRecordModel
    ): Either<NetworkError, List<HistoryModel>> = repositoryContext {
        client.get(ApiRoutes.History.GET_HISTORY) {
            setBody(getHistoryPressureRecordModel)
        }.handleResponse<List<HistoryModel>>()
    }

    override suspend fun restoreRecordFromHistory(
        model: RestoreHistoryModel
    ): Either<NetworkError, HistoryModel> = repositoryContext {
        client.post(ApiRoutes.History.RESTORE_FROM_HISTORY) {
            setBody(model)
        }.handleResponse<HistoryModel>()
    }
}