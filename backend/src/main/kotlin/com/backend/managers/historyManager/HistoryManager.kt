package com.backend.managers.historyManager

import com.example.api.models.GetHistoryPressureRecordModel
import com.example.api.models.HistoryModel
import com.example.api.models.RestoreHistoryModel
import com.example.managers.SimpleManager
import java.util.UUID

interface GetHistoryForRecordManager : HistoryManager<GetHistoryPressureRecordModel, Unit, List<HistoryModel>>
interface RestoreRecordFromHistoryManager : HistoryManager<UUID, RestoreHistoryModel, HistoryModel>

interface HistoryManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
