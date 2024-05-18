package com.mist.mobile_app.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mist.mobile_app.ui.components.PDLoaderIndicator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter


@Composable
fun LazyListState.OnBottomReachedWithIndicator(
    isCanAction: Boolean,
    action: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    OnBottomReachedWithIndicator(
        isCanAction = isCanAction,
        shouldLoadMore = shouldLoadMore.value,
        action = action
    )
}

@Composable
fun OnBottomReachedWithIndicator(
    isCanAction: Boolean,
    shouldLoadMore: Boolean,
    action: () -> Unit
) {
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore }
            .distinctUntilChanged()
            .filter { it }
            .collectLatest {
                if (isCanAction) {
                    action()
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (isCanAction) {
            PDLoaderIndicator(
                circleColor = Color.Black
            )
        }
    }
}