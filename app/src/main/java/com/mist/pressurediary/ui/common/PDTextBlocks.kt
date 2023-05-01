package com.mist.pressurediary.ui.common

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.mist.pressurediary.utils.VoiceIntentUtils

@Composable
fun PDBlockWithTitle(
    modifier: Modifier = Modifier,
    value: String,
    title: String,
    placeholder: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.background,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value.ifEmpty {
                placeholder
            },
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            color = if (value.isEmpty()) {
                MaterialTheme.colors.surface
            } else {
                MaterialTheme.colors.background
            },
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PDBlockEntryBottomText(
    modifier: Modifier = Modifier,
    title: String,
    iconId: Int,
    value: String,
    placeholder: String,
    horizontalPadding: Dp = 15.dp,
    onValueChange: (String) -> Unit,
) {
    val voiceLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            VoiceIntentUtils.voiceLauncher(
                activityResult = it,
                onValueChange = onValueChange
            )
        }

    PDBackgroundBlock(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        onClick = {
            voiceLauncher.launch(VoiceIntentUtils.voiceIntent)
        }
    ) {
        Column(
            modifier = Modifier
                .sizeIn(
                    minHeight = 52.dp
                )
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(
                            horizontal = horizontalPadding,
                            vertical = 10.dp
                        )
                        .weight(1f, false),
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.background,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                modifier = Modifier
                    .padding(
                        horizontal = horizontalPadding,
                    )
                    .padding(
                        bottom = 10.dp
                    ),
                text = value.ifEmpty {
                    placeholder
                },
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                color = if (value.isEmpty()) {
                    MaterialTheme.colors.surface
                } else {
                    MaterialTheme.colors.background
                },
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PDBlockEntry(
    modifier: Modifier = Modifier,
    iconId: Int,
    value: String,
    title: String,
    horizontalPadding: Dp,
    placeholder: String,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
) {
    val voiceLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            VoiceIntentUtils.voiceLauncher(
                activityResult = it,
                onValueChange = onValueChange
            )
        }

    PDBackgroundBlock(
        modifier = modifier,
        onClick = {
            if (onClick != null) {
                onClick()
            } else {
                voiceLauncher.launch(VoiceIntentUtils.voiceIntent)
            }
        }
    ) {
        Row(
            modifier = Modifier
                .sizeIn(
                    minHeight = 50.dp
                )
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .weight(1f, false),
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = Color.Unspecified
            )
            PDBlockWithTitle(
                modifier = Modifier,
                value = value,
                title = title,
                placeholder = placeholder
            )
        }
    }
}