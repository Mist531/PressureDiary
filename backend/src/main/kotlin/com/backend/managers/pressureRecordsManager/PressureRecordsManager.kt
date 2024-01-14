package com.backend.managers.pressureRecordsManager

import com.example.api.models.*
import com.example.managers.SimpleManager
import io.ktor.http.*
import java.util.*

interface PostPressureRecordManager : PressureRecordManager<UUID, PostPressureRecordModel, HttpStatusCode>
interface DeletePressureRecordManager : PressureRecordManager<Unit, DeletePressureRecordModel, HttpStatusCode>
interface PutPressureRecordManager : PressureRecordManager<Unit, PutPressureRecordModel, HttpStatusCode>
interface GetPaginatedPressureRecordsManager :
    PressureRecordManager<UUID, GetPaginatedPressureRecordsModel, List<PressureRecordModel>>

interface PressureRecordManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
