package com.mist.pressurediary.ui.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Text
import com.mist.pressurediary.data.stores.PressureDiaryStore

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
) {
    var list by remember {
        mutableStateOf(listOf<PressureDiaryStore.PressureDiaryTable>())
    }
    LaunchedEffect(Unit){
        list = PressureDiaryStore.getAllEntry()
    }
    ScalingLazyColumn(
        state = rememberScalingLazyListState(),
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        items(list) { item ->
            Text(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                text = item.toString()
            )
        }
    }
}