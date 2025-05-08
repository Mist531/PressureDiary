package io.pressurediary.android.wear.ui.common

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
    onDateChange: (LocalDate) -> Unit,
    startDate: LocalDate? = null
) {
    DatePicker(
        modifier = Modifier.fillMaxSize(),
        onDateConfirm = onDateChange,
        date = startDate ?: LocalDate.now(),
        toDate = LocalDate.now().plusDays(1),
        fromDate = MIN_DATE
    )
}

@Composable
fun PDTimePicker(
    onTimeChange: (LocalTime) -> Unit,
    startTime: LocalTime? = null
) {
    TimePicker(
        modifier = Modifier.fillMaxSize(),
        onTimeConfirm = onTimeChange,
        time = startTime ?: LocalTime.now(),
        showSeconds = false
    )
}