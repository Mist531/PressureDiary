package com.mist.mobile_app.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mist.common.data.stores.impl.TokensDataStore
import com.mist.mobile_app.ui.components.PDCircularLoader
import de.palm.composestateevents.EventEffect
import org.koin.compose.getKoin

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    tokensDataStore: TokensDataStore = getKoin().get(),
    onGoToLogin: () -> Unit = {},
    onGoToMain: () -> Unit = {}
) {
    val state by tokensDataStore.checkInitialRouteState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        tokensDataStore.checkAndUpdateData()
    }

    EventEffect(
        event = state,
        onConsumed = tokensDataStore::onConsumedInitialRoute
    ) { bool ->
        when (bool) {
            true -> onGoToLogin.invoke()
            else -> onGoToMain.invoke()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PDCircularLoader()
    }
}