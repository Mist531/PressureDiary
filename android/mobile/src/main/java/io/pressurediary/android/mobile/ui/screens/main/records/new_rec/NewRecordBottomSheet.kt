package io.pressurediary.android.mobile.ui.screens.main.records.new_rec

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.pressurediary.android.mobile.ui.components.PDCircularLoader
import io.pressurediary.android.mobile.ui.components.PDModalBottomSheet
import io.pressurediary.android.mobile.ui.screens.main.records.RecordContent
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecordBottomSheet(
    modifier: Modifier = Modifier,
    onCloseBottomSheet: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = false,
    viewModel: NewRecordViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EventEffect(
        event = state.closeBottomSheetEvent,
        onConsumed = viewModel::onConsumedBottomSheetEvent
    ) {
        viewModel.clearState()
        onDismissRequest()
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    PDModalBottomSheet(
        modifier = modifier.testTag("NewRecordBottomSheet"),
        sheetState = sheetState,
        isVisible = isVisible,
        isFillMaxSize = false,
        onDismissRequest = {
            viewModel.clearState()
            onCloseBottomSheet()
        },
    ) {
        AnimatedContent(
            modifier = Modifier
                .height(360.dp),
            targetState = state.showProgressBar,
            label = ""
        ) { showProgressBar ->
            if (showProgressBar) {
                Box(
                    modifier = modifier.testTag("ProgressBar"),
                    contentAlignment = Alignment.Center
                ) {
                    PDCircularLoader()
                }
            } else {
                RecordContent(
                    modifier = Modifier,
                    systolic = state.systolic?.toString() ?: "",
                    setSystolic = viewModel::setSystolic,
                    isSystolicError = state.isSystolicError,
                    diastolic = state.diastolic?.toString() ?: "",
                    setDiastolic = viewModel::setDiastolic,
                    isDiastolicError = state.isDiastolicError,
                    pulse = state.pulse?.toString() ?: "",
                    setPulse = viewModel::setPulse,
                    isPulseError = state.isPulseError,
                    note = state.note,
                    setNote = viewModel::setNote,
                    onSaveClick = viewModel::onSaveRecord,
                )
            }
        }
    }
}