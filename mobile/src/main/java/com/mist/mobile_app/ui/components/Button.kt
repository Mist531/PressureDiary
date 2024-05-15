package com.mist.mobile_app.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mist.common.ui.PDColors
import com.mist.mobile_app.ui.theme.PDTheme

@Composable
fun PDButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = remember(enabled) {
                if (enabled) PDColors.orange else PDColors.grey
            }
        ),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 5.dp
        )
    ) {
        Text(
            text = text,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun PreviewPDButton() {
    PDTheme {
        PDButton(
            text = "Test"
        )
    }
}