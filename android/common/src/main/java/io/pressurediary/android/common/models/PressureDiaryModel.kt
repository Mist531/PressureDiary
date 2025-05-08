package io.pressurediary.android.common.models

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.none
import arrow.optics.optics
import io.pressurediary.android.common.data.bd.PressureDiaryStore
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
        diastolic = diastolic.getOrElse { 0 },
        systolic = systolic.getOrElse { 0 },
        pulse = pulse.getOrElse { 0 },
        date = date,
        time = time,
        comment = comment
    )
}