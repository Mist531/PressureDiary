package com.backend.managers.deviceManager

import com.example.api.models.DeleteUserDeviceModel
import com.example.api.models.DeviceModel
import com.example.api.models.PostDeviceForUserModel
import com.example.managers.SimpleManager
import io.ktor.http.HttpStatusCode
import java.util.UUID

interface GetUserDevicesListManager : DeviceManager<UUID, Unit, List<DeviceModel>>
interface PostDeviceForUserManager : DeviceManager<UUID, PostDeviceForUserModel, HttpStatusCode>
interface DeleteUserDeviceManager : DeviceManager<Unit, DeleteUserDeviceModel, HttpStatusCode>

interface DeviceManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
