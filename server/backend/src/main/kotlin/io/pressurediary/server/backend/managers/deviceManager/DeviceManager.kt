package io.pressurediary.server.backend.managers.deviceManager

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.DeleteUserDeviceModel
import io.pressurediary.server.api.models.DeviceModel
import io.pressurediary.server.api.models.PostDeviceForUserModel
import io.pressurediary.server.backend.managers.SimpleManager
import java.util.UUID

interface GetUserDevicesListManager : DeviceManager<UUID, Unit, List<DeviceModel>>
interface PostDeviceForUserManager : DeviceManager<UUID, PostDeviceForUserModel, HttpStatusCode>
interface DeleteUserDeviceManager : DeviceManager<Unit, DeleteUserDeviceModel, HttpStatusCode>

interface DeviceManager<Param, Request, Response> : SimpleManager<Param, Request, Response>