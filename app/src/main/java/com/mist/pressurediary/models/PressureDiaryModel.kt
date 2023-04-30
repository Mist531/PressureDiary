package com.mist.pressurediary.models

import arrow.core.Option
import arrow.core.none
import arrow.optics.optics
import com.mist.pressurediary.data.stores.PressureDiaryStore
import com.mist.pressurediary.utils.getLocalDateTimeNow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
@optics
data class PressureDiaryModel(
    val id: UUID = UUID(),
    val diastolic: Option<Int> = none(),
    val systolic: Option<Int> = none(),
    val pulse: Option<Int> = none(),
    val date: LocalDate = getLocalDateTimeNow().date,
    val time: LocalTime = getLocalDateTimeNow().time,
    val comment: String = ""
){
    fun mapToTable() = PressureDiaryStore.PressureDiaryTable(
        id = id,
        diastolic = diastolic.orNull() ?: 0,
        systolic = systolic.orNull() ?: 0,
        pulse = pulse.orNull() ?: 0,
        date = date,
        time = time,
        comment = comment
    )
}