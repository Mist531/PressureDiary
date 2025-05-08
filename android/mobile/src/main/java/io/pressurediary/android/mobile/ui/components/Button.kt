package io.pressurediary.android.mobile.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pressurediary.android.common.ui.PDColors
import io.pressurediary.android.mobile.ui.theme.PDTheme

@Composable
fun PDButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    containerColor: Color = PDColors.orange
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled)
                containerColor
            else
                PDColors.grey
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
private fun PreviewPDButton() {
    PDTheme {
        PDButton(
            text = "Test"
        )
    }
}