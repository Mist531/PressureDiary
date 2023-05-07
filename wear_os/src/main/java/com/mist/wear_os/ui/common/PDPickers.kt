package com.mist.wear_os.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.horologist.composables.DatePicker
import com.google.android.horologist.composables.TimePicker
import java.time.LocalDate
import java.time.LocalTime

val MIN_DATE: LocalDate = LocalDate.of(1900, 1, 1)

@Composable
fun PDDatePicker(
    onDateChange: (LocalDate) -> Unit
) {
    DatePicker(
        modifier = Modifier.fillMaxSize(),
        onDateConfirm = { date ->
            onDateChange(date)
        },
        date = LocalDate.now(),
        toDate = LocalDate.now().plusDays(1),
        fromDate = MIN_DATE
    )
}

@Composable
fun PDTimePicker(
    onTimeChange: (LocalTime) -> Unit
) {
    TimePicker(
        modifier = Modifier.fillMaxSize(),
        onTimeConfirm = { time ->
            onTimeChange(time)
        },
        time = LocalTime.now(),
        showSeconds = false
    )
}