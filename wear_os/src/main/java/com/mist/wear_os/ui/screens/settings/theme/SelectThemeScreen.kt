package com.mist.wear_os.ui.screens.settings.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.RadioButton
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import com.mist.wear_os.theme.Theme
import com.mist.wear_os.utils.ScalingLazyColumnPadding
import org.koin.androidx.compose.getViewModel

@Composable
fun SelectThemeScreen(
    modifier: Modifier = Modifier,
    viewModel: SelectThemeViewModel = getViewModel()
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
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = ScalingLazyColumnPadding
        ) {
            items(Theme.listTheme) { theme ->
                ToggleChip(
                    modifier = Modifier.fillMaxWidth(),
                    checked = state.theme == theme,
                    onCheckedChange = {
                        viewModel.setTheme(theme)
                    },
                    label = {
                        Text(
                            text = stringResource(id = theme.title),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colors.background,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    toggleControl = {
                        RadioButton(
                            selected = state.theme == theme,
                        )
                    },
                    colors = ToggleChipDefaults.toggleChipColors(
                        checkedStartBackgroundColor = MaterialTheme.colors.primary,
                        checkedToggleControlColor = MaterialTheme.colors.primary
                    )
                )
            }
        }
    }
}