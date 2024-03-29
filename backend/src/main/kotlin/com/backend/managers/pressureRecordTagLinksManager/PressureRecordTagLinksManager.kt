package com.backend.managers.pressureRecordTagLinksManager

import com.example.api.models.AddPressureRecordTagLinkModel
import com.example.api.models.DeletePressureRecordTagLinkByRecordModel
import com.example.api.models.DeletePressureRecordTagLinkByTagModel
import com.example.managers.SimpleManager
import io.ktor.http.*

interface AddPressureRecordTagLinkManager : PressureRecordTagLinksManager<Unit, AddPressureRecordTagLinkModel, HttpStatusCode>
interface DeletePressureRecordTagLinkByRecordManager : PressureRecordTagLinksManager<Unit, DeletePressureRecordTagLinkByRecordModel, HttpStatusCode>
interface DeletePressureRecordTagLinkByTagManager : PressureRecordTagLinksManager<Unit, DeletePressureRecordTagLinkByTagModel, HttpStatusCode>

interface PressureRecordTagLinksManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
