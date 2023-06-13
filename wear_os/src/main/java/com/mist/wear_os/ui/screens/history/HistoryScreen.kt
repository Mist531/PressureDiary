package com.mist.wear_os.ui.screens.history

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
import com.mist.common.models.PressureDiaryModel
import com.mist.wear_os.R
import com.mist.wear_os.ui.common.PDBackgroundBlock
import com.mist.wear_os.utils.ScalingLazyColumnPadding
import com.mist.wear_os.utils.dateInFormat
import com.mist.wear_os.utils.getStringValueOptionInt
import com.mist.wear_os.utils.timeInFormat
import kotlinx.uuid.UUID
import org.koin.androidx.compose.getViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    onEntryClick: (UUID) -> Unit,
    viewModel: HistoryViewModel = getViewModel()
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
                                text = stringResource(R.string.history_date, map.key.dateInFormat()),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
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
                text = """
                    ${stringResource(R.string.history_time, entry.time.timeInFormat())}
                    ${stringResource(R.string.history_systolic, entry.systolic.getStringValueOptionInt())}
                    ${stringResource(R.string.history_diastolic, entry.diastolic.getStringValueOptionInt())}
                    ${stringResource(R.string.history_heart_rate, entry.pulse.getStringValueOptionInt())}
                    ${stringResource(R.string.history_comment, entry.comment)}
                """.trimIndent(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = MaterialTheme.colors.background,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
