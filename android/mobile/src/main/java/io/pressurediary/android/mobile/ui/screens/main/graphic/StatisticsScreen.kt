package io.pressurediary.android.mobile.ui.screens.main.graphic

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import io.pressurediary.android.common.ui.PDColors
import io.pressurediary.android.mobile.ui.components.PDCircularLoader
import io.pressurediary.android.mobile.ui.screens.main.graphic.charts.Chart
import io.pressurediary.android.mobile.ui.screens.main.graphic.charts.PDChartType
import io.pressurediary.android.mobile.ui.screens.main.history.HistoryViewModel
import io.pressurediary.android.mobile.ui.screens.main.history.StatisticPage
import io.pressurediary.server.api.models.PressureRecordModel
import org.koin.androidx.compose.getViewModel

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    eventRefreshData: StateEvent = consumed,
    onConsumedEventRefreshData: () -> Unit = {},
    viewModel: HistoryViewModel = getViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EventEffect(
        event = eventRefreshData,
        onConsumed = onConsumedEventRefreshData
    ) {
        viewModel.onRefresh()
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StatisticsPageBlock(
            modifier = Modifier,
            selectedStatisticPage = state.selectedStatisticPage,
            onSetStatisticPage = viewModel::onSetStatisticsPage
        )

        StatisticsScreenContent(
            showProgressBar = state.showProgressBarChart,
            selectedStatisticPage = state.selectedStatisticPage,
            records = state.allPressureRecords,
            onRefresh = viewModel::onRefresh,
            isRefreshing = state.isRefreshing
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatisticsScreenContent(
    modifier: Modifier = Modifier,
    showProgressBar: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    selectedStatisticPage: StatisticPage,
    records: List<PressureRecordModel>,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        AnimatedContent(
            targetState = showProgressBar,
            label = ""
        ) { showProgressBar ->
            if (showProgressBar) {
                Box(
                    modifier = modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    PDCircularLoader()
                }
            } else {
                Box(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    StatisticsChartBlock(
                        records = records,
                        selectedStatisticPage = selectedStatisticPage
                    )

                    PullRefreshIndicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        refreshing = isRefreshing,
                        state = pullRefreshState,
                        contentColor = Color.Black,
                        scale = true
                    )
                }
            }
        }
    }
}

@Composable
fun StatisticsChartBlock(
    modifier: Modifier = Modifier,
    records: List<PressureRecordModel>,
    selectedStatisticPage: StatisticPage,
) {
    val info = remember(selectedStatisticPage, records) {
        when (selectedStatisticPage) {
            StatisticPage.HEART_RATE -> PDChartType.HeartRate(
                records = records
            )

            StatisticPage.BLOOD_PRESSURE -> PDChartType.BloodPressure(
                records = records
            )
        }
    }

    StatisticsChartWrapper(
        modifier = modifier
            .padding(
                vertical = 15.dp,
                horizontal = 20.dp
            )
    ) {
        Chart(
            info = info
        )
    }
}

@Composable
fun StatisticsChartWrapper(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 1.dp,
                color = PDColors.greyBorder,
                shape = RoundedCornerShape(24.dp)
            )
            .background(
                PDColors.white
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = 25.dp,
                vertical = 15.dp
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        content()
    }
}

@Composable
fun StatisticsPageBlock(
    modifier: Modifier = Modifier,
    selectedStatisticPage: StatisticPage,
    onSetStatisticPage: (StatisticPage) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ItemStatisticsPageIndicator(
            modifier = Modifier
                .weight(1f),
            title = "Давление",
            onSetStatisticPage = onSetStatisticPage,
            selectedStatisticPage = selectedStatisticPage,
            statisticPage = StatisticPage.BLOOD_PRESSURE,
        )
        ItemStatisticsPageIndicator(
            modifier = Modifier
                .weight(1f),
            title = "Пульс",
            onSetStatisticPage = onSetStatisticPage,
            selectedStatisticPage = selectedStatisticPage,
            statisticPage = StatisticPage.HEART_RATE,
        )
    }
}

@Composable
fun ItemStatisticsPageIndicator(
    modifier: Modifier = Modifier,
    title: String,
    statisticPage: StatisticPage,
    selectedStatisticPage: StatisticPage,
    onSetStatisticPage: (StatisticPage) -> Unit,
) {
    val color by animateColorAsState(
        targetValue = if (selectedStatisticPage == statisticPage)
            PDColors.indicatorGrey
        else
            PDColors.indicatorGrey.copy(alpha = 0f),
        label = ""
    )

    Column(
        modifier = modifier
            .wrapContentHeight()
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp
                )
            )
            .clickable(
                onClick = {
                    onSetStatisticPage(statisticPage)
                },
                role = Role.Button
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .padding(
                    bottom = 8.dp
                ),
            text = title,
            color = PDColors.black,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        StatisticsPageIndicator(
            color = color
        )
    }
}

@Composable
fun StatisticsPageIndicator(
    modifier: Modifier = Modifier,
    color: Color = PDColors.indicatorGrey
) {
    HorizontalDivider(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = 100.dp,
                    topEnd = 100.dp
                )
            ),
        thickness = 7.dp,
        color = color
    )
}

@Preview
@Composable
private fun PreviewStatisticsPageIndicator() {
    StatisticsPageIndicator()
}

@Preview
@Composable
private fun PreviewStatisticsPage() {
    var selectedStatisticPage by remember {
        mutableStateOf(StatisticPage.HEART_RATE)
    }

    StatisticsPageBlock(
        selectedStatisticPage = selectedStatisticPage,
        onSetStatisticPage = {
            selectedStatisticPage = it
        }
    )
}