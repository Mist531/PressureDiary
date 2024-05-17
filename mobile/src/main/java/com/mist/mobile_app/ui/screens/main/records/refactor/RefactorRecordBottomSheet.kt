package com.mist.mobile_app.ui.screens.main.records.refactor

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.api.models.PressureRecordModel
import com.mist.mobile_app.R
import com.mist.mobile_app.ui.components.PDButton
import com.mist.mobile_app.ui.components.PDModalBottomSheet
import com.mist.mobile_app.ui.components.PDTextField
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefactorRecordBottomSheet(
    modifier: Modifier = Modifier,
    pressureRecord: PressureRecordModel?,
    onCloseBottomSheet: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false,
    viewModel: RefactorRecordViewModel = koinViewModel(
        parameters = { parametersOf(pressureRecord) },
        key = pressureRecord?.pressureRecordUUID.toString()
    )
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EventEffect(
        event = state.closeBottomSheetEvent,
        onConsumed = viewModel::onConsumedBottomSheetEvent
    ) {
        onDismissRequest()
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    PDModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        isVisible = isVisible,
        isFillMaxSize = false,
        onDismissRequest = onCloseBottomSheet,
    ) {
        val defModifier = Modifier
            .padding(
                bottom = 15.dp
            )

        Text(text = "Редактирование записи")

        ItemNewRecord(
            modifier = defModifier,
            value = state.pressureRecord?.systolic?.toString() ?: "",
            onValueChange = viewModel::setSystolic,
            title = "SYS.",
            iconId = com.mist.common.R.drawable.ic_systolic,
        )
        ItemNewRecord(
            modifier = defModifier,
            value = state.pressureRecord?.diastolic?.toString()?: "",
            onValueChange = viewModel::setDiastolic,
            title = "DIA.",
            iconId = com.mist.common.R.drawable.ic_diastolic,
        )
        ItemNewRecord(
            modifier = defModifier,
            value = state.pressureRecord?.pulse?.toString()?: "",
            onValueChange = viewModel::setPulse,
            title = stringResource(R.string.new_record_pulse),
            iconId = com.mist.common.R.drawable.ic_heart
        )
        ItemNewRecord(
            modifier = defModifier,
            value = state.pressureRecord?.note ?: "",
            onValueChange = viewModel::setNote,
            title = stringResource(R.string.new_record_comment),
            iconId = com.mist.common.R.drawable.ic_comment,
            maxLines = 5,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        PDButton(
            modifier = defModifier,
            text = stringResource(R.string.btn_save),
            onClick = {
                viewModel.onSaveRecord()
            }
        )
    }
}

@Composable
fun ItemNewRecord(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next
    ),
    maxLines: Int = 1,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    end = 10.dp,
                    top = 14.dp
                )
                .size(28.dp),
            painter = painterResource(id = iconId),
            contentDescription = null
        )
        PDTextField(
            modifier = Modifier
                .width(300.dp),
            value = value,
            onValueChange = onValueChange,
            title = title,
            placeholder = null,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
        )
    }
}