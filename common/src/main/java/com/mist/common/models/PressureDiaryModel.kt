package com.mist.common.models

import arrow.core.Option
import arrow.core.none
import arrow.optics.optics
import com.mist.common.data.stores.PressureDiaryStore
import kotlinx.uuid.UUID
import java.time.LocalDate
import java.time.LocalTime

@optics
data class PressureDiaryModel(
    val id: UUID = UUID(),
    val diastolic: Option<Int> = none(),
    val systolic: Option<Int> = none(),
    val pulse: Option<Int> = none(),
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val comment: String = ""
) {
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