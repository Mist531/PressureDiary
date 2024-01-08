package com.backend.managers.pressureRecordTagLinksManager

import com.backend.models.AddPressureRecordTagLinkModel
import com.backend.models.DeletePressureRecordTagLinkByRecordModel
import com.backend.models.DeletePressureRecordTagLinkByTagModel
import com.example.managers.SimpleManager
import io.ktor.http.*

interface AddPressureRecordTagLinkManager : PressureRecordTagLinksManager<Unit, AddPressureRecordTagLinkModel, HttpStatusCode>
interface DeletePressureRecordTagLinkByRecordManager : PressureRecordTagLinksManager<Unit, DeletePressureRecordTagLinkByRecordModel, HttpStatusCode>
interface DeletePressureRecordTagLinkByTagManager : PressureRecordTagLinksManager<Unit, DeletePressureRecordTagLinkByTagModel, HttpStatusCode>

interface PressureRecordTagLinksManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
