package io.pressurediary.android.wear.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import io.pressurediary.android.common.models.PressureDiaryModel
import io.pressurediary.android.wear.R
import io.pressurediary.android.wear.ui.common.PDBackgroundBlock
import io.pressurediary.android.wear.utils.ScalingLazyColumnPadding
import io.pressurediary.android.wear.utils.dateInFormat
import io.pressurediary.android.wear.utils.getStringValueOptionInt
import io.pressurediary.android.wear.utils.timeInFormat
import kotlinx.uuid.UUID
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    onEntryClick: (UUID) -> Unit,
    viewModel: HistoryViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val scalingLazyListState = rememberScalingLazyListState(
        initialCenterItemIndex = 0
    )

    Scaffold(
        modifier = modifier,
        positionIndicator = {
            PositionIndicator(scalingLazyListState = scalingLazyListState)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.showProgressBar.not() && state.groupList.isEmpty()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stringResource(R.string.history_empty),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary
                )
            } else if (state.showProgressBar.not() && state.groupList.isNotEmpty()) {
                ScalingLazyColumn(
                    state = scalingLazyListState,
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = ScalingLazyColumnPadding
                ) {
                    state.groupList.forEach { map ->
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                text = stringResource(
                                    R.string.history_date,
                                    map.key.dateInFormat()
                                ),
                            )
                        }
                        items(map.value) { entry ->
                            HistoryInfoBlock(
                                entry = entry,
                                onClick = {
                                    onEntryClick(entry.id)
                                }
                            )
                        }
                    }
                }
            } else if (state.showProgressBar) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
fun HistoryInfoBlock(
    modifier: Modifier = Modifier,
    entry: PressureDiaryModel,
    onClick: () -> Unit
) {
    PDBackgroundBlock(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(20.dp)
                )
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = """
                    ${stringResource(R.string.history_time, entry.time.timeInFormat())}
                    ${
                    stringResource(
                        R.string.history_systolic,
                        entry.systolic.getStringValueOptionInt()
                    )
                }
                    ${
                    stringResource(
                        R.string.history_diastolic,
                        entry.diastolic.getStringValueOptionInt()
                    )
                }
                    ${
                    stringResource(
                        R.string.history_heart_rate,
                        entry.pulse.getStringValueOptionInt()
                    )
                }
                    ${stringResource(R.string.history_comment, entry.comment)}
                """.trimIndent(),
                color = MaterialTheme.colors.background,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
