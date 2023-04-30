package com.mist.pressurediary.ui.screens.create

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import arrow.core.some
import com.mist.pressurediary.models.PressureDiaryModel
import com.mist.pressurediary.ui.common.PDRowButton
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UpdateEntryScreen(
    modifier: Modifier = Modifier,
    entry: PressureDiaryModel,
    viewModel: CreateOrUpdateEntryViewModel = getViewModel {
        parametersOf(entry.some())
    }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val scalingLazyListState = rememberScalingLazyListState(
        initialCenterItemIndex = 0
    )

    val context = LocalContext.current

    fun toast() = Toast.makeText(
        context,
        "Please enter a valid number",
        Toast.LENGTH_SHORT
    ).show()

    Scaffold(
        modifier = modifier,
        positionIndicator = {
            PositionIndicator(scalingLazyListState = scalingLazyListState)
        }
    ) {
        ContentEntryScreen(
            modifier = Modifier.fillMaxWidth(),
            diastolicValue = (state.entry.diastolic.orNull() ?: "").toString(),
            onDiastolicValueChange = {str->
                if (str.toIntOrNull() != null) {
                    viewModel.onDiastolicChanged(str.toInt())
                } else {
                    toast()
                }
            },
            systolicValue = (state.entry.systolic.orNull() ?: "").toString(),
            onSystolicValueChange = { str ->
                if (str.toIntOrNull() != null) {
                    viewModel.onSystolicChanged(str.toInt())
                } else {
                    toast()
                }
            },
            pulseValue = (state.entry.pulse.orNull() ?: "").toString(),
            onPulseValueChange = { str ->
                if (str.toIntOrNull() != null) {
                    viewModel.onPulseChanged(str.toInt())
                } else {
                    toast()
                }
            },
            dateValue = state.entry.date.toString(),
            //onDateValueChange = ,
            timeValue = state.entry.time.toString(),
            //onTimeValueChange = ,
            commentValue = state.entry.comment,
            onCommentValueChange = viewModel::onCommentChanged,
            scalingLazyListState = scalingLazyListState,
            bottomContent = {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        PDRowButton(
                            //TODO
                        )
                        PDRowButton(
                            //TODO
                        )
                    }
                }
            }
        )
    }
}