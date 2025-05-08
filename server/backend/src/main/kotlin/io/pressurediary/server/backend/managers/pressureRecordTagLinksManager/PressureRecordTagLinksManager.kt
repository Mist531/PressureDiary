package io.pressurediary.server.backend.managers.pressureRecordTagLinksManager

import io.pressurediary.server.backend.managers.SimpleManager
import io.pressurediary.server.api.models.AddPressureRecordTagLinkModel
import io.pressurediary.server.api.models.DeletePressureRecordTagLinkByRecordModel
import io.pressurediary.server.api.models.DeletePressureRecordTagLinkByTagModel
import io.ktor.http.HttpStatusCode

interface AddPressureRecordTagLinkManager :
    PressureRecordTagLinksManager<Unit, AddPressureRecordTagLinkModel, HttpStatusCode>

interface DeletePressureRecordTagLinkByRecordManager :
    PressureRecordTagLinksManager<Unit, DeletePressureRecordTagLinkByRecordModel, HttpStatusCode>

interface DeletePressureRecordTagLinkByTagManager :
    PressureRecordTagLinksManager<Unit, DeletePressureRecordTagLinkByTagModel, HttpStatusCode>

interface PressureRecordTagLinksManager<Param, Request, Response> :
    SimpleManager<Param, Request, Response>
