package com.mist.wear_os.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import com.mist.wear_os.ui.common.PDRowButton

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onGoToCreate: () -> Unit,
    onGoToHistory: () -> Unit,
    onGoToSettings: () -> Unit
) {
    val listButton: List<Pair<Int, () -> Unit>> = listOf(
        Pair(com.mist.common.R.drawable.ic_add, onGoToCreate),
        Pair(com.mist.common.R.drawable.ic_history, onGoToHistory),
        Pair(com.mist.common.R.drawable.ic_settings, onGoToSettings)
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            listButton.forEach { buttonInfo ->
                PDRowButton(
                    modifier = Modifier
                        .padding(4.dp),
                    onClick = buttonInfo.second,
                ) {
                    Icon(
                        painter = painterResource(id = buttonInfo.first),
                        contentDescription = null,
                        tint = MaterialTheme.colors.background
                    )
                }
            }
        }
    }
}