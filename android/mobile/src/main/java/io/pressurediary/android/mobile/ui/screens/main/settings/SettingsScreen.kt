package io.pressurediary.android.mobile.ui.screens.main.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.palm.composestateevents.EventEffect
import io.pressurediary.android.common.ui.PDColors
import io.pressurediary.android.mobile.ui.components.PDButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onLogOut: () -> Unit = {},
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EventEffect(
        event = state.logOutEvent,
        onConsumed = viewModel::onConsumedLogOut,
        action = onLogOut,
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PDButton(
            modifier = Modifier
                .padding(
                    bottom = 20.dp
                ),
            text = "Удалить аккаунт",
            containerColor = PDColors.error,
            onClick = viewModel::onDeleteAcc
        )
        PDButton(
            modifier = Modifier,
            text = "Выйти",
            onClick = viewModel::onLogOut
        )
    }
}