package com.backend.managersImpl

import com.backend.managers.pressureRecordsManager.DeletePressureRecordManager
import com.backend.managers.pressureRecordsManager.GetPaginatedPressureRecordsManager
import com.backend.managers.pressureRecordsManager.PostPressureRecordManager
import com.backend.managers.pressureRecordsManager.PutPressureRecordManager
import com.example.api.models.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

interface PressureRecordManager {
    suspend fun addPressureRecord(userId: UUID, model: PostPressureRecordModel): HttpStatusCode
    suspend fun deletePressureRecord(model: DeletePressureRecordModel): HttpStatusCode
    suspend fun editPressureRecord(model: PutPressureRecordModel): HttpStatusCode
    suspend fun getPaginatedPressureRecords(
        userId: UUID,
        model: GetPaginatedPressureRecordsModel
    ): List<PressureRecordModel>
}

class PressureRecordManagerImpl : PressureRecordManager, KoinComponent {

    override suspend fun addPressureRecord(
        userId: UUID,
        model: PostPressureRecordModel
    ): HttpStatusCode {
        val manager: PostPressureRecordManager by inject()
        return runCatching {
            manager.invoke(userId, model)
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
            manager.invoke(Unit, model)
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
            manager.invoke(Unit, model)
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
            manager.invoke(userId, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }
}
