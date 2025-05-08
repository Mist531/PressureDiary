package io.pressurediary.android.mobile.ui.screens.main.history

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import io.pressurediary.android.common.ui.PDColors
import io.pressurediary.android.mobile.ui.components.PDCircularLoader
import io.pressurediary.android.mobile.ui.screens.main.records.refactor.RefactorRecordBottomSheet
import io.pressurediary.android.mobile.utils.OnBottomReachedWithIndicator
import io.pressurediary.server.api.models.PressureRecordModel
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    eventRefreshData: StateEvent = consumed,
    onConsumedEventRefreshData: () -> Unit = {},
    viewModel: HistoryViewModel = getViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = viewModel::onRefresh
    )

    EventEffect(
        event = eventRefreshData,
        onConsumed = onConsumedEventRefreshData,
        action = viewModel::onRefresh,
    )

    var refactorRecord by remember {
        mutableStateOf<PressureRecordModel?>(null)
    }

    var isVisibleRefactorRecord by remember {
        mutableStateOf(false)
    }

    RefactorRecordBottomSheet(
        pressureRecord = refactorRecord,
        isVisible = isVisibleRefactorRecord,
        onDismissRequest = {
            isVisibleRefactorRecord = false
            viewModel.onRefresh()
        },
        onCloseBottomSheet = {
            isVisibleRefactorRecord = false
        }
    )

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedContent(
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
                HistoryScreenContent(
                    modifier = modifier,
                    onClickRecord = { record ->
                        refactorRecord = record
                        isVisibleRefactorRecord = true
                    },
                    pressureRecords = state.pressureRecords,
                    isCanLoadNextPage = state.isCanLoadNextPage,
                    onNextPageReviews = viewModel::nextPagePressureDiary
                )
            }
        }

        PullRefreshIndicator(
            modifier = Modifier
                .testTag("pullToRefresh")
                .align(Alignment.TopCenter),
            refreshing = state.isRefreshing,
            state = pullRefreshState,
            contentColor = Color.Black,
            scale = true
        )
    }
}

@Composable
fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    isCanLoadNextPage: Boolean,
    onClickRecord: (PressureRecordModel) -> Unit = {},
    onNextPageReviews: () -> Unit,
    pressureRecords: Map<LocalDate, List<PressureRecordModel>> = emptyMap()
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        pressureRecords.forEach { map ->
            item {
                val date = remember(map.key) {
                    map.key.format(
                        DateTimeFormatter.ofPattern(
                            "dd.MM.yyyy"
                        )
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 10.dp
                        ),
                    text = date,
                    color = PDColors.black,
                    fontWeight = FontWeight.Bold,
                )
            }
            itemsIndexed(
                items = map.value,
                key = { _, item ->
                    item.dateTimeRecord.hashCode().toString()
                }
            ) { index, record ->
                val isLastItem = index == map.value.size - 1

                HistoryCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 5.dp
                        )
                        .then(
                            if (isLastItem)
                                Modifier
                            else
                                Modifier.padding(
                                    bottom = 5.dp
                                )
                        ),
                    record = record,
                    onClick = {
                        onClickRecord(record)
                    }
                )
            }
        }
        item {
            lazyListState.OnBottomReachedWithIndicator(
                isCanAction = isCanLoadNextPage,
                action = onNextPageReviews,
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

