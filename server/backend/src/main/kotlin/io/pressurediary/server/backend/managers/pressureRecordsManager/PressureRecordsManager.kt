package io.pressurediary.server.backend.managers.pressureRecordsManager

import io.pressurediary.server.backend.managers.SimpleManager
import io.pressurediary.server.api.models.DeletePressureRecordModel
import io.pressurediary.server.api.models.GetAllPressureRecordsModel
import io.pressurediary.server.api.models.GetPaginatedPressureRecordsModel
import io.pressurediary.server.api.models.PostPressureRecordModel
import io.pressurediary.server.api.models.PressureRecordModel
import io.pressurediary.server.api.models.PutPressureRecordModel
import io.ktor.http.HttpStatusCode
import java.util.UUID

interface PostPressureRecordManager :
    PressureRecordManager<UUID, PostPressureRecordModel, HttpStatusCode>

interface DeletePressureRecordManager :
    PressureRecordManager<Unit, DeletePressureRecordModel, HttpStatusCode>

interface PutPressureRecordManager :
    PressureRecordManager<Unit, PutPressureRecordModel, HttpStatusCode>

interface GetPaginatedPressureRecordsManager :
    PressureRecordManager<UUID, GetPaginatedPressureRecordsModel, List<PressureRecordModel>>

interface GetAllPressureRecordsManager :
    PressureRecordManager<UUID, GetAllPressureRecordsModel, List<PressureRecordModel>>

interface PressureRecordManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
