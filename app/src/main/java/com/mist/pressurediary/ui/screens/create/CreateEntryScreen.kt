package com.mist.pressurediary.ui.screens.create

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import arrow.core.none
import com.mist.pressurediary.models.PressureDiaryModel
import com.mist.pressurediary.ui.common.PDBackgroundBlock
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CreateEntryScreen(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    viewModel: CreateOrUpdateEntryViewModel = getViewModel {
        parametersOf(
            none<PressureDiaryModel>()
        )
    }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val scalingLazyListState = rememberScalingLazyListState(
        initialCenterItemIndex = 0
    )

    val context = LocalContext.current

    fun toast() = Toast.makeText( //TODO: Error flow
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
                item{
                    PDBackgroundBlock(
                        modifier = Modifier
                            .width(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primary),
                        onClick = {
                            viewModel.onPostEntry()
                            onGoBack()
                        }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            text = "Save",
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.background,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        )
    }
}