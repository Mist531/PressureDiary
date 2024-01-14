package com.mist.mobile_app.ui.screens.main.history

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.api.models.PressureRecordModel
import com.mist.common.ui.PDColors
import com.mist.mobile_app.ui.components.PDLoader
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = getViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    AnimatedContent(
        targetState = state.showProgressBar,
        label = ""
    ) { showProgressBar ->
        if (showProgressBar) {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                PDLoader()
            }
        } else {
            HistoryScreenContent(
                modifier = modifier,
                pressureRecords = state.pressureRecords
            )
        }
    }
}

@Composable
fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    pressureRecords: Map<LocalDate, List<PressureRecordModel>> = emptyMap()
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = "History size: ${pressureRecords.size}",
                color = PDColors.Black,
                fontWeight = FontWeight.Bold,
            )
        }
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
                    color = PDColors.Black,
                    fontWeight = FontWeight.Bold,
                )
            }
            itemsIndexed(
                items = map.value,
                key = { _, item ->
                    item.dateTimeRecord
                }
            ) { index, record ->
                val isLastItem = remember(map.value) {
                    index == map.value.size - 1
                }

                HistoryCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 5.dp
                        )
                        .then(
                            if (isLastItem) Modifier
                            else Modifier.padding(
                                bottom = 5.dp
                            )
                        ),
                    record = record
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

