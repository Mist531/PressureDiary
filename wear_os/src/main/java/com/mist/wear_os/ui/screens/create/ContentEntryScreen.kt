package com.mist.wear_os.ui.screens.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListScope
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import com.mist.wear_os.R
import com.mist.wear_os.ui.common.PDBlockEntry
import com.mist.wear_os.ui.common.PDBlockEntryBottomText
import com.mist.wear_os.ui.common.PDDatePicker
import com.mist.wear_os.ui.common.PDTimePicker
import com.mist.wear_os.utils.ScalingLazyColumnPadding
import com.mist.wear_os.utils.isTriggered
import de.palm.composestateevents.StateEvent
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun ContentEntryScreen(
    modifier: Modifier = Modifier,
    datePickerEvent: StateEvent,
    onDatePickerEvent: () -> Unit,
    onConsumedDatePickerWithSave: (LocalDate) -> Unit,
    timePickerEvent: StateEvent,
    onTimePickerEvent: () -> Unit,
    onConsumedTimePickerWithSave: (LocalTime) -> Unit,
    diastolicValue: String,
    onDiastolicValueChange: (String) -> Unit,
    systolicValue: String,
    onSystolicValueChange: (String) -> Unit,
    pulseValue: String,
    onPulseValueChange: (String) -> Unit,
    dateValue: String,
    timeValue: String,
    commentValue: String,
    onCommentValueChange: (String) -> Unit,
    scalingLazyListState: ScalingLazyListState,
    bottomContent: ScalingLazyListScope.() -> Unit
) {
    AnimatedVisibility(visible = timePickerEvent.isTriggered()) {
        PDTimePicker(
            onTimeChange = onConsumedTimePickerWithSave
        )
    }

    AnimatedVisibility(visible = datePickerEvent.isTriggered()) {
        PDDatePicker(
            onDateChange = onConsumedDatePickerWithSave
        )
    }

    AnimatedVisibility(
        visible = !datePickerEvent.isTriggered() && !timePickerEvent.isTriggered()
    ) {
        ScalingLazyColumn(
            state = scalingLazyListState,
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = ScalingLazyColumnPadding,
        ) {
            item {
                PDBlockEntry(
                    modifier = Modifier,
                    iconId = R.drawable.ic_diastolic,
                    value = diastolicValue,
                    title = stringResource(R.string.content_entry_diastolic),
                    onValueChange = onDiastolicValueChange,
                    horizontalPadding = 18.dp,
                    placeholder = stringResource(R.string.content_entry_entry_diastolic),
                    onClick = null
                )
            }
            item {
                PDBlockEntry(
                    modifier = Modifier,
                    iconId = R.drawable.ic_systolic,
                    value = systolicValue,
                    title = stringResource(R.string.content_entry_systolic),
                    onValueChange = onSystolicValueChange,
                    horizontalPadding = 18.dp,
                    placeholder = stringResource(R.string.content_entry_entry_systolic),
                    onClick = null
                )
            }
            item {
                PDBlockEntry(
                    modifier = Modifier,
                    iconId = R.drawable.ic_heart,
                    value = pulseValue,
                    horizontalPadding = 15.dp,
                    title = stringResource(R.string.content_entry_heart_rate),
                    onValueChange = onPulseValueChange,
                    placeholder = stringResource(R.string.content_entry_pl_entry_heart_rate),
                    onClick = null
                )
            }
            item {
                PDBlockEntry(
                    modifier = Modifier,
                    iconId = R.drawable.ic_calendar,
                    value = dateValue,
                    title = stringResource(R.string.content_entry_date),
                    horizontalPadding = 17.dp,
                    placeholder = stringResource(R.string.content_entry_pl_entry_date),
                    onClick = onDatePickerEvent
                )
            }
            item {
                PDBlockEntry(
                    modifier = Modifier,
                    iconId = R.drawable.ic_clock,
                    value = timeValue,
                    title = stringResource(R.string.content_entry_time),
                    placeholder = stringResource(R.string.content_entry_pl_entry_time),
                    horizontalPadding = 15.dp,
                    onClick = onTimePickerEvent
                )
            }
            item {
                PDBlockEntryBottomText(
                    modifier = Modifier,
                    iconId = R.drawable.ic_comment,
                    value = commentValue,
                    title = stringResource(R.string.content_entry_comment),
                    placeholder = stringResource(R.string.content_entry_pl_entry_comment),
                    onValueChange = onCommentValueChange
                )
            }
            bottomContent()
        }
    }
}