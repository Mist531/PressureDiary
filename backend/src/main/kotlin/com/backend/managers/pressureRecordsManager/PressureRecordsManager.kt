package com.backend.managers.pressureRecordsManager

import com.backend.managers.SimpleManager
import com.example.api.models.DeletePressureRecordModel
import com.example.api.models.GetAllPressureRecordsModel
import com.example.api.models.GetPaginatedPressureRecordsModel
import com.example.api.models.PostPressureRecordModel
import com.example.api.models.PressureRecordModel
import com.example.api.models.PutPressureRecordModel
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
