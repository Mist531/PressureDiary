package com.mist.pressurediary.models

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ContentEntryModel(
    val iconId: Int,
    val value: String,
    val title: String,
    val onValueChange: (String) -> Unit,
    val horizontalPadding: Dp = 15.dp,
    val placeholder: String,
    val onClick: (() -> Unit)? = null,
)