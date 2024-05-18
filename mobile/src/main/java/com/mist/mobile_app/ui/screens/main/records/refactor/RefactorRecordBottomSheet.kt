package com.mist.mobile_app.ui.screens.main.records.refactor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.api.models.PressureRecordModel
import com.mist.mobile_app.ui.components.PDCircularLoader
import com.mist.mobile_app.ui.components.PDModalBottomSheet
import com.mist.mobile_app.ui.screens.main.records.RecordContent
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
        AnimatedContent(
            modifier = Modifier
                .imePadding()
                .height(400.dp),
            targetState = state.showProgressBar,
            label = ""
        ) { showProgressBar ->
            if (showProgressBar) {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    PDCircularLoader()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                bottom = 16.dp
                            ),
                        text = "Редактирование записи"
                    )
                    RecordContent(
                        systolic = state.pressureRecord?.systolic?.toString() ?: "",
                        setSystolic = viewModel::setSystolic,
                        diastolic = state.pressureRecord?.diastolic?.toString() ?: "",
                        setDiastolic = viewModel::setDiastolic,
                        pulse = state.pressureRecord?.pulse?.toString() ?: "",
                        setPulse = viewModel::setPulse,
                        note = state.pressureRecord?.note ?: "",
                        setNote = viewModel::setNote,
                        onSaveClick = viewModel::onSaveRecord,
                    )
                }
            }
        }
    }
}

