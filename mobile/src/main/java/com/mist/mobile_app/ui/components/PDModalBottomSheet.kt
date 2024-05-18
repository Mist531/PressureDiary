package com.mist.mobile_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mist.common.ui.PDColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PDModalBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    isVisible: Boolean,
    canDismissed: Boolean = true,
    onDismissRequest: () -> Unit,
    shape: Shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    containerColor: Color = Color.White,
    showDragHandler: Boolean = true,
    isFillMaxSize: Boolean = true,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    if (isVisible) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = {
                if (canDismissed) {
                    onDismissRequest()
                } else {
                    coroutineScope.launch {
                        sheetState.show()
                    }
                }
            },
            sheetState = sheetState,
            shape = shape,
            dragHandle = null,
            containerColor = Color.Transparent,
            windowInsets = WindowInsets(0, 0, 0, 0)
        ) {
            Column(
                modifier = Modifier
                    .systemBarsPadding()

                    .let {
                        if (isFillMaxSize) {
                            it.fillMaxHeight()
                        } else {
                            it.wrapContentHeight()
                        }
                    }
                    .fillMaxWidth()
                    .clip(shape = shape)
                    .background(color = containerColor, shape = shape),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showDragHandler) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(
                            modifier = Modifier
                                .padding(
                                    top = 10.dp,
                                    bottom = 28.dp
                                )
                                .size(width = 38.dp, height = 5.dp)
                                .clip(CircleShape)
                                .background(color = PDColors.grey),
                        )
                    }
                }
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppointmentConformationDialogPreview() {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var isVisible by remember {
        mutableStateOf(true)
    }

    PDModalBottomSheet(
        modifier = Modifier,
        sheetState = sheetState,
        isVisible = isVisible,
        onDismissRequest = {
            isVisible = false
        }
    )
}