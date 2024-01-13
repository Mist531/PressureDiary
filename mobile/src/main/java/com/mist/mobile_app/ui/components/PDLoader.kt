package com.mist.mobile_app.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mist.common.ui.PDColors

@Composable
fun PDLoader(
    modifier: Modifier = Modifier,
    color: Color = PDColors.Black
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color
    )
}

@Preview
@Composable
fun PDLoaderPreview() {
    PDLoader()
}