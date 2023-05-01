package com.mist.pressurediary.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import arrow.core.Option
import de.palm.composestateevents.StateEvent
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun StateEvent.isTriggered() = this is StateEvent.Triggered

fun toast(
    context: Context,
    text: String
) = Toast.makeText( //TODO: Error flow
    context,
    text,
    Toast.LENGTH_SHORT
).show()

val ScalingLazyColumnPadding = PaddingValues(
    top = 25.dp,
    start = 20.dp,
    end = 20.dp,
)

fun Option<Int>.getStringValueOptionInt(
) = this.fold(
    ifSome = { it.toString() },
    ifEmpty = { "" }
)

private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

private val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun LocalDate.dateInFormat(): String = this.format(dateFormat)

fun LocalTime.timeInFormat(): String = this.format(timeFormat)