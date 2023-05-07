package com.mist.wear_os.ui.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.ScalingLazyListScope
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import arrow.core.none
import arrow.core.some
import com.mist.wear_os.R
import com.mist.wear_os.ui.common.PDBackgroundBlock
import com.mist.wear_os.ui.common.PDRowButton
import com.mist.wear_os.utils.dateInFormat
import com.mist.wear_os.utils.getStringValueOptionInt
import com.mist.wear_os.utils.timeInFormat
import com.mist.wear_os.utils.toast
import de.palm.composestateevents.EventEffect
import kotlinx.uuid.UUID
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CreateOrUpdateEntryScreen(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    id: UUID? = null,
    viewModel: CreateOrUpdateEntryViewModel = getViewModel {
        parametersOf(
            id?.some() ?: none<UUID>()
        )
    }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val scalingLazyListState = rememberScalingLazyListState(
        initialCenterItemIndex = 0
    )

    val context = LocalContext.current

    EventEffect(
        event = state.saveEvent,
        onConsumed = viewModel::onConsumedSaveEvent,
    ) {
        viewModel.onPostEntry()
        onGoBack()
    }

    EventEffect(
        event = state.updateEvent,
        onConsumed = viewModel::onConsumedUpdateEvent,
    ) {
        viewModel.onUpdateEntry()
        onGoBack()
    }

    EventEffect(
        event = state.deleteEvent,
        onConsumed = viewModel::onConsumedDeleteEvent
    ) {
        viewModel.onDeleteEntry()
        onGoBack()
    }

    Scaffold(
        modifier = modifier,
        positionIndicator = {
            PositionIndicator(scalingLazyListState = scalingLazyListState)
        }
    ) {
        val errorMessage = stringResource(R.string.error_please_enter_a_valid_number)
        ContentEntryScreen(
            modifier = Modifier.fillMaxWidth(),
            datePickerEvent = state.datePickerEvent,
            onDatePickerEvent = viewModel::onDatePickerEvent,
            onConsumedDatePickerWithSave = viewModel::onConsumedDatePickerEventWithSave,
            timePickerEvent = state.timePickerEvent,
            onTimePickerEvent = viewModel::onTimePickerEvent,
            onConsumedTimePickerWithSave = viewModel::onConsumedTimePickerEventWithSave,
            diastolicValue = state.entry.diastolic.getStringValueOptionInt(),
            onDiastolicValueChange = { str ->
                if (str.toIntOrNull() != null) {
                    viewModel.onDiastolicChanged(str.toInt())
                } else {
                    toast(
                        context = context,
                        text = errorMessage
                    )
                }
            },
            systolicValue = state.entry.systolic.getStringValueOptionInt(),
            onSystolicValueChange = { str ->
                if (str.toIntOrNull() != null) {
                    viewModel.onSystolicChanged(str.toInt())
                } else {
                    toast(
                        context = context,
                        text = errorMessage
                    )
                }
            },
            pulseValue = state.entry.pulse.getStringValueOptionInt(),
            onPulseValueChange = { str ->
                if (str.toIntOrNull() != null) {
                    viewModel.onPulseChanged(str.toInt())
                } else {
                    toast(
                        context = context,
                        text = errorMessage
                    )
                }
            },
            dateValue = state.entry.date.dateInFormat(),
            timeValue = state.entry.time.timeInFormat(),
            commentValue = state.entry.comment,
            onCommentValueChange = viewModel::onCommentChanged,
            scalingLazyListState = scalingLazyListState,
            bottomContent = {
                createOrUpdateEntryBottomContent(
                    id = id,
                    onSaveClick = viewModel::onSaveEvent,
                    onUpdateClick = viewModel::onUpdateEvent,
                    onDeleteClick = viewModel::onDeleteEvent,
                )
            }
        )
    }
}

fun ScalingLazyListScope.createOrUpdateEntryBottomContent(
    id: UUID?,
    onSaveClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit,
){
    if (id != null) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PDRowButton(
                    modifier = Modifier.padding(end = 10.dp),
                    onClick = onDeleteClick,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    },
                    backgroundColor = MaterialTheme.colors.onError
                )
                //TODO: add dialog for confirmation of changes
                PDRowButton(
                    onClick = onUpdateClick,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_ok),
                            contentDescription = null,
                            tint = MaterialTheme.colors.background
                        )
                    }
                )
            }
        }
    } else {
        item {
            PDBackgroundBlock(
                modifier = Modifier
                    .width(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary),
                onClick = onSaveClick
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    text = stringResource(id = R.string.btn_save),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.background,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}