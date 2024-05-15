package com.mist.mobile_app.ui.screens.main.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mist.mobile_app.ui.components.PDButton
import de.palm.composestateevents.EventEffect
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
    ) {
        onLogOut()
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        PDButton(
            modifier = Modifier,
            text = "Выйти",
            onClick = viewModel::logOut
        )
    }
}