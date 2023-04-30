package com.mist.pressurediary.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme

@Composable
fun PDRowButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    icon: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(60.dp)
            .clickable {
                onClick()
            }
            .background(
                color = MaterialTheme.colors.primary,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}