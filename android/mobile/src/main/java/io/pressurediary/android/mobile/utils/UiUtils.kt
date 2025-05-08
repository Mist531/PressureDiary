package io.pressurediary.android.mobile.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pressurediary.android.mobile.ui.components.PDLoaderIndicator
import io.pressurediary.android.mobile.ui.theme.PDTheme
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

private const val DEFAULT_PRELOAD_VALUE = 7

@Composable
fun LazyListState.OnBottomReachedWithIndicator(
    modifier: Modifier = Modifier,
    isCanAction: Boolean,
    isLoading: Boolean,
    preloadValue: Int = DEFAULT_PRELOAD_VALUE,
    action: () -> Unit
) {
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true
            lastVisibleItem.index >= layoutInfo.totalItemsCount - preloadValue
        }
    }

    OnBottomReachedWithIndicator(
        modifier = modifier,
        isCanAction = isCanAction,
        shouldLoadMore = shouldLoadMore,
        action = action,
        isLoading = isLoading
    )
}

@Composable
fun LazyGridState.OnBottomReachedWithIndicator(
    modifier: Modifier = Modifier,
    isCanAction: Boolean,
    isLoading: Boolean,
    preloadValue: Int = DEFAULT_PRELOAD_VALUE,
    action: () -> Unit
) {
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true
            lastVisibleItem.index == layoutInfo.totalItemsCount - preloadValue
        }
    }

    OnBottomReachedWithIndicator(
        modifier = modifier,
        isCanAction = isCanAction,
        shouldLoadMore = shouldLoadMore,
        action = action,
        isLoading = isLoading
    )
}

@Composable
fun OnBottomReachedWithIndicator(
    modifier: Modifier = Modifier,
    isCanAction: Boolean,
    shouldLoadMore: Boolean,
    isLoading: Boolean,
    action: () -> Unit
) {
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow {
            shouldLoadMore
        }.distinctUntilChanged().collectLatest {
            if (isCanAction) {
                action()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            PDLoaderIndicator(
                circleColor = Color.Black
            )
        }
    }
}

@Preview
@Composable
private fun PreviewOnBottomReachedWithIndicator() {
    PDTheme {
        val lazyListState = rememberLazyListState()

        var list by remember {
            mutableStateOf(List(size = 40, init = Int::toString))
        }

        val isCanAction = remember(list.size) {
            list.size < 100
        }

        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            verticalArrangement = Arrangement
                .spacedBy(16.dp),
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
            state = lazyListState
        ) {
            items(
                items = list,
                key = { it }
            ) { it ->
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    textAlign = TextAlign.Center,
                )
            }
            item {
                lazyListState.OnBottomReachedWithIndicator(
                    modifier = Modifier
                        .padding(
                            bottom = 8.dp
                        ),
                    preloadValue = 20,
                    isCanAction = isCanAction,
                    action = {
                        list = list + List(list.size * 2, Int::toString)
                    },
                    isLoading = true
                )
            }
        }
    }
}