package com.backend.managers.pressureRecordTagLinksManager

import com.backend.managers.SimpleManager
import com.example.api.models.AddPressureRecordTagLinkModel
import com.example.api.models.DeletePressureRecordTagLinkByRecordModel
import com.example.api.models.DeletePressureRecordTagLinkByTagModel
import io.ktor.http.HttpStatusCode

interface AddPressureRecordTagLinkManager :
    PressureRecordTagLinksManager<Unit, AddPressureRecordTagLinkModel, HttpStatusCode>

interface DeletePressureRecordTagLinkByRecordManager :
    PressureRecordTagLinksManager<Unit, DeletePressureRecordTagLinkByRecordModel, HttpStatusCode>

interface DeletePressureRecordTagLinkByTagManager :
    PressureRecordTagLinksManager<Unit, DeletePressureRecordTagLinkByTagModel, HttpStatusCode>

interface PressureRecordTagLinksManager<Param, Request, Response> :
    SimpleManager<Param, Request, Response>
