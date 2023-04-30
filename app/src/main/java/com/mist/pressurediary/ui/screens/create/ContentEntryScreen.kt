package com.mist.pressurediary.ui.screens.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListScope
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import com.mist.pressurediary.R
import com.mist.pressurediary.models.ContentEntryModel
import com.mist.pressurediary.ui.common.PDBlockEntry
import com.mist.pressurediary.ui.common.PDBlockEntryText

@Composable
fun ContentEntryScreen(
    modifier: Modifier = Modifier,
    diastolicValue: String,
    onDiastolicValueChange: (String) -> Unit,
    systolicValue: String,
    onSystolicValueChange: (String) -> Unit,
    pulseValue: String,
    onPulseValueChange: (String) -> Unit,
    dateValue: String,
    //onDateValueChange = ,
    timeValue: String,
    //onTimeValueChange = ,
    commentValue: String,
    onCommentValueChange: (String) -> Unit,
    scalingLazyListState: ScalingLazyListState,
    bottomContent: ScalingLazyListScope.() -> Unit
) {
    val contentEntryList = listOf(
        ContentEntryModel(
            iconId = R.drawable.ic_diastolic,
            title = "Diastolic",
            value = diastolicValue,
            onValueChange = onDiastolicValueChange,
            horizontalPadding = 18.dp,
            placeholder = "Entry diastolic"
        ),
        ContentEntryModel(
            iconId = R.drawable.ic_systolic,
            title = "Systolic",
            value = systolicValue,
            onValueChange = onSystolicValueChange,
            horizontalPadding = 18.dp,
            placeholder = "Entry systolic"
        ),
        ContentEntryModel(
            iconId = R.drawable.ic_heart,
            title = "Heart Rate",
            value = pulseValue,
            onValueChange = onPulseValueChange,
            placeholder = "Entry heart rate"
        ),
        ContentEntryModel(
            iconId = R.drawable.ic_calendar,
            title = "Date",
            value = dateValue,
            onValueChange = { /*TODO*/ },
            horizontalPadding = 17.dp,
            placeholder = "Entry date",
            onClick = {
                /*TODO*/
            }
        ),
        ContentEntryModel(
            iconId = R.drawable.ic_clock,
            title = "Time",
            value = timeValue,
            onValueChange = { /*TODO*/ },
            placeholder = "Entry time",
            onClick = {
                /*TODO*/
            }
        ),
    )

    ScalingLazyColumn(
        state = scalingLazyListState,
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        items(contentEntryList) { model ->
            PDBlockEntry(
                modifier = Modifier,
                iconId = model.iconId,
                value = model.value,
                title = model.title,
                onValueChange = model.onValueChange,
                horizontalPadding = model.horizontalPadding,
                placeholder = model.placeholder,
                onClick = model.onClick
            )
        }
        item {
            PDBlockEntryText(
                modifier = Modifier,
                iconId = R.drawable.ic_comment,
                value = commentValue,
                title = "Comment",
                placeholder = "Entry comment",
                onValueChange = onCommentValueChange
            )
        }
        bottomContent()
    }
}