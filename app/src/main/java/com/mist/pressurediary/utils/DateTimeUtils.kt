package com.mist.pressurediary.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getLocalDateTimeNow() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())