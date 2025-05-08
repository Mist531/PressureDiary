package io.pressurediary.android.mobile.ui.screens.main.records.refactor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.palm.composestateevents.EventEffect
import io.pressurediary.android.common.ui.PDColors
import io.pressurediary.android.mobile.ui.components.PDCircularLoader
import io.pressurediary.android.mobile.ui.components.PDModalBottomSheet
import io.pressurediary.android.mobile.ui.screens.main.records.RecordContent
import io.pressurediary.server.api.models.PressureRecordModel
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
        onConsumed = viewModel::onConsumedBottomSheetEvent,
        action = onDismissRequest
    )

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
                .height(415.dp),
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(
                                bottom = 16.dp
                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center),
                            text = "Редактирование записи"
                        )
                        IconButton(
                            modifier = Modifier
                                .align(
                                    Alignment.CenterEnd
                                )
                                .padding(
                                    end = 20.dp
                                ),
                            onClick = viewModel::onDeleteRecord
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = io.pressurediary.android.common.R.drawable.ic_delete
                                ),
                                contentDescription = "",
                                tint = PDColors.error
                            )
                        }
                    }
                    RecordContent(
                        systolic = state.pressureRecord?.systolic?.toString() ?: "",
                        setSystolic = viewModel::setSystolic,
                        isSystolicError = state.isSystolicError,
                        diastolic = state.pressureRecord?.diastolic?.toString() ?: "",
                        setDiastolic = viewModel::setDiastolic,
                        isDiastolicError = state.isDiastolicError,
                        pulse = state.pressureRecord?.pulse?.toString() ?: "",
                        setPulse = viewModel::setPulse,
                        isPulseError = state.isPulseError,
                        note = state.pressureRecord?.note ?: "",
                        setNote = viewModel::setNote,
                        onSaveClick = viewModel::onSaveRecord,
                    )
                }
            }
        }
    }
}

