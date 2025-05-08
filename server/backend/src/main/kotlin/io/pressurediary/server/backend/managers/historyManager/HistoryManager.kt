package io.pressurediary.server.backend.managers.historyManager

import io.pressurediary.server.backend.managers.SimpleManager
import io.pressurediary.server.api.models.GetHistoryPressureRecordModel
import io.pressurediary.server.api.models.HistoryModel
import io.pressurediary.server.api.models.RestoreHistoryModel
import java.util.UUID

interface GetHistoryForRecordManager :
    HistoryManager<GetHistoryPressureRecordModel, Unit, List<HistoryModel>>

interface RestoreRecordFromHistoryManager : HistoryManager<UUID, RestoreHistoryModel, HistoryModel>

interface HistoryManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
