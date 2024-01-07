package com.backend.managers.pressureRecordsManager

import com.backend.models.*
import com.example.managers.SimpleManager
import io.ktor.http.*
import java.util.*

interface PostPressureRecordManager : PressureRecordManager<UUID, PostPressureRecordModel, HttpStatusCode>
interface DeletePressureRecordManager : PressureRecordManager<Unit, DeletePressureRecordModel, HttpStatusCode>
interface PutPressureRecordManager : PressureRecordManager<Unit, PutPressureRecordModel, HttpStatusCode>
interface GetPaginatedPressureRecordsManager :
    PressureRecordManager<Unit, GetPaginatedPressureRecordsModel, List<PressureRecordModel>>

interface PressureRecordManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
