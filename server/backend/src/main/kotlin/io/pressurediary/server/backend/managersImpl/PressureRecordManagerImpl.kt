package io.pressurediary.server.backend.managersImpl

import io.pressurediary.server.backend.managers.pressureRecordsManager.DeletePressureRecordManager
import io.pressurediary.server.backend.managers.pressureRecordsManager.GetAllPressureRecordsManager
import io.pressurediary.server.backend.managers.pressureRecordsManager.GetPaginatedPressureRecordsManager
import io.pressurediary.server.backend.managers.pressureRecordsManager.PostPressureRecordManager
import io.pressurediary.server.backend.managers.pressureRecordsManager.PutPressureRecordManager
import io.pressurediary.server.api.models.DeletePressureRecordModel
import io.pressurediary.server.api.models.GetAllPressureRecordsModel
import io.pressurediary.server.api.models.GetPaginatedPressureRecordsModel
import io.pressurediary.server.api.models.PostPressureRecordModel
import io.pressurediary.server.api.models.PressureRecordModel
import io.pressurediary.server.api.models.PutPressureRecordModel
import io.ktor.http.HttpStatusCode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

interface PressureRecordManager {
    suspend fun addPressureRecord(userId: UUID, model: PostPressureRecordModel): HttpStatusCode
    suspend fun deletePressureRecord(model: DeletePressureRecordModel): HttpStatusCode
    suspend fun editPressureRecord(model: PutPressureRecordModel): HttpStatusCode
    suspend fun getPaginatedPressureRecords(
        userId: UUID,
        model: GetPaginatedPressureRecordsModel
    ): List<PressureRecordModel>

    suspend fun getAllPressureRecords(
        userId: UUID,
        model: GetAllPressureRecordsModel
    ): List<PressureRecordModel>
}

class PressureRecordManagerImpl : PressureRecordManager, KoinComponent {

    override suspend fun addPressureRecord(
        userId: UUID,
        model: PostPressureRecordModel
    ): HttpStatusCode {
        val manager: PostPressureRecordManager by inject()
        return runCatching {
            manager.request(userId, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.Created
        }
    }

    override suspend fun deletePressureRecord(model: DeletePressureRecordModel): HttpStatusCode {
        val manager: DeletePressureRecordManager by inject()
        return runCatching {
            manager.request(Unit, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun editPressureRecord(model: PutPressureRecordModel): HttpStatusCode {
        val manager: PutPressureRecordManager by inject()
        return runCatching {
            manager.request(Unit, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun getPaginatedPressureRecords(
        userId: UUID,
        model: GetPaginatedPressureRecordsModel
    ): List<PressureRecordModel> {
        val manager: GetPaginatedPressureRecordsManager by inject()
        return runCatching {
            manager.request(userId, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun getAllPressureRecords(
        userId: UUID,
        model: GetAllPressureRecordsModel
    ): List<PressureRecordModel> {
        val manager: GetAllPressureRecordsManager by inject()
        return runCatching {
            manager.request(userId, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}
