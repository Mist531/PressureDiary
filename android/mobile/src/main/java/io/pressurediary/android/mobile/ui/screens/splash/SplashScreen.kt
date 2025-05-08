package io.pressurediary.android.mobile.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.palm.composestateevents.EventEffect
import io.pressurediary.android.common.data.stores.impl.TokensDataStore
import io.pressurediary.android.mobile.ui.components.PDCircularLoader
import org.koin.compose.getKoin

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    tokensDataStore: TokensDataStore = getKoin().get(),
    onGoToLogin: () -> Unit = {},
    onGoToMain: () -> Unit = {}
) {
    val state by tokensDataStore.checkInitialRouteState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        tokensDataStore.checkAndUpdateData()
    }

    EventEffect(
        event = state,
        onConsumed = tokensDataStore::onConsumedInitialRoute
    ) { bool ->
        if (bool == true)
            onGoToLogin()
        else
            onGoToMain()
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PDCircularLoader()
    }
}