package com.backend.managers.deviceManager

import com.backend.models.DeleteUserDeviceModel
import com.backend.models.DeviceModel
import com.backend.models.PostDeviceForUserModel
import com.example.managers.SimpleManager
import io.ktor.http.*
import java.util.*

interface GetUserDevicesListManager : DeviceManager<UUID, Unit, List<DeviceModel>>
interface PostDeviceForUserManager : DeviceManager<UUID, PostDeviceForUserModel, HttpStatusCode>
interface DeleteUserDeviceManager : DeviceManager<Unit, DeleteUserDeviceModel, HttpStatusCode>

interface DeviceManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
