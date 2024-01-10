package com.backend.managersImpl

import com.backend.managers.pressureRecordTagLinksManager.AddPressureRecordTagLinkManager
import com.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByRecordManager
import com.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByTagManager
import com.example.api.models.AddPressureRecordTagLinkModel
import com.example.api.models.DeletePressureRecordTagLinkByRecordModel
import com.example.api.models.DeletePressureRecordTagLinkByTagModel
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface PressureRecordTagLinksManager {
    suspend fun addPressureRecordTagLink(addPressureRecordTagLinkModel: AddPressureRecordTagLinkModel): HttpStatusCode
    suspend fun deletePressureRecordTagLinkByRecord(deletePressureRecordTagLinkByRecordModel: DeletePressureRecordTagLinkByRecordModel): HttpStatusCode
    suspend fun deletePressureRecordTagLinkByTag(deletePressureRecordTagLinkByTagModel: DeletePressureRecordTagLinkByTagModel): HttpStatusCode
}

class PressureRecordTagLinksManagerImpl : PressureRecordTagLinksManager, KoinComponent {
    override suspend fun addPressureRecordTagLink(
        addPressureRecordTagLinkModel: AddPressureRecordTagLinkModel
    ): HttpStatusCode {
        val manager: AddPressureRecordTagLinkManager by inject()
        return runCatching {
            manager.invoke(Unit, addPressureRecordTagLinkModel)
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
            manager.invoke(Unit, deletePressureRecordTagLinkByRecordModel)
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
            manager.invoke(Unit, deletePressureRecordTagLinkByTagModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }
}
