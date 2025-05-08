package io.pressurediary.server.backend.managersImpl

import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.AddPressureRecordTagLinkManager
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByRecordManager
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByTagManager
import io.pressurediary.server.api.models.AddPressureRecordTagLinkModel
import io.pressurediary.server.api.models.DeletePressureRecordTagLinkByRecordModel
import io.pressurediary.server.api.models.DeletePressureRecordTagLinkByTagModel
import io.ktor.http.HttpStatusCode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface PressureRecordTagLinksManager {
    suspend fun addPressureRecordTagLink(
        addPressureRecordTagLinkModel: AddPressureRecordTagLinkModel
    ): HttpStatusCode

    suspend fun deletePressureRecordTagLinkByRecord(
        deletePressureRecordTagLinkByRecordModel: DeletePressureRecordTagLinkByRecordModel
    ): HttpStatusCode

    suspend fun deletePressureRecordTagLinkByTag(
        deletePressureRecordTagLinkByTagModel: DeletePressureRecordTagLinkByTagModel
    ): HttpStatusCode
}

class PressureRecordTagLinksManagerImpl : PressureRecordTagLinksManager, KoinComponent {
    override suspend fun addPressureRecordTagLink(
        addPressureRecordTagLinkModel: AddPressureRecordTagLinkModel
    ): HttpStatusCode {
        val manager: AddPressureRecordTagLinkManager by inject()
        return runCatching {
            manager.request(Unit, addPressureRecordTagLinkModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun deletePressureRecordTagLinkByRecord(
        deletePressureRecordTagLinkByRecordModel: DeletePressureRecordTagLinkByRecordModel
    ): HttpStatusCode {
        val manager: DeletePressureRecordTagLinkByRecordManager by inject()
        return runCatching {
            manager.request(Unit, deletePressureRecordTagLinkByRecordModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun deletePressureRecordTagLinkByTag(
        deletePressureRecordTagLinkByTagModel: DeletePressureRecordTagLinkByTagModel
    ): HttpStatusCode {
        val manager: DeletePressureRecordTagLinkByTagManager by inject()
        return runCatching {
            manager.request(Unit, deletePressureRecordTagLinkByTagModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }
}
