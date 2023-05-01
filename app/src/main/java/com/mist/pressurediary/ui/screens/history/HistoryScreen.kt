package com.mist.pressurediary.ui.screens.history

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
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.mist.pressurediary.R
import com.mist.pressurediary.models.PressureDiaryModel
import com.mist.pressurediary.ui.common.PDBackgroundBlock
import com.mist.pressurediary.utils.ScalingLazyColumnPadding
import com.mist.pressurediary.utils.dateInFormat
import com.mist.pressurediary.utils.getStringValueOptionInt
import com.mist.pressurediary.utils.timeInFormat
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
        if (state.groupList.isEmpty()){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.history_empty),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary
                )
            }
        }else{
            ScalingLazyColumn(
                state = scalingLazyListState,
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = ScalingLazyColumnPadding
            ) {
                state.groupList.forEach {map->
                    item {
                        Text(
                            text = stringResource(R.string.history_date) + ": ${map.key.dateInFormat()}",
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
        }
    }
}

@Composable
fun HistoryInfoBlock(
    modifier: Modifier = Modifier,
    entry: PressureDiaryModel,
    onClick: () -> Unit
){
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
                    ${stringResource(R.string.content_entry_time)}: ${entry.time.timeInFormat()}
                    ${stringResource(R.string.content_entry_diastolic)}: ${entry.diastolic.getStringValueOptionInt()}
                    ${stringResource(R.string.content_entry_systolic)}: ${entry.systolic.getStringValueOptionInt()}
                    ${stringResource(R.string.content_entry_heart_rate)}: ${entry.pulse.getStringValueOptionInt()}
                    ${stringResource(R.string.content_entry_comment)}: ${entry.comment}
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
