package com.mist.wear_os.ui.screens.settings.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import com.mist.wear_os.R
import com.mist.wear_os.ui.common.PDBackgroundBlock
import com.mist.wear_os.ui.common.PDBlockWithTitle
import com.mist.wear_os.utils.ScalingLazyColumnPadding
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateToSelectTheme: () -> Unit,
    viewModel: SettingsViewModel = getViewModel()
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
        ScalingLazyColumn(
            state = scalingLazyListState,
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = ScalingLazyColumnPadding
        ) {
            item {
                SettingBlock(
                    value = state.theme.let { theme ->
                        if (theme != null) {
                            stringResource(id = theme.title)
                        } else ""
                    },
                    title = stringResource(R.string.settings_theme),
                    onClick = navigateToSelectTheme
                )
            }
        }
    }
}

@Composable
fun SettingBlock(
    title: String,
    value: String,
    onClick: () -> Unit,
) {
    PDBackgroundBlock(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.primary,
                shape = CircleShape
            ),
        onClick = onClick
    ) {
        PDBlockWithTitle(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .fillMaxWidth(),
            title = title,
            value = value,
        )
    }
}