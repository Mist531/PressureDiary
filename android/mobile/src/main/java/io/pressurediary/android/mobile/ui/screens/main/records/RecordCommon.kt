package io.pressurediary.android.mobile.ui.screens.main.records

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.pressurediary.android.mobile.R
import io.pressurediary.android.mobile.ui.components.PDButton
import io.pressurediary.android.mobile.ui.components.PDTextField

@Composable
fun RecordContent(
    modifier: Modifier = Modifier,
    systolic: String,
    setSystolic: (String) -> Unit,
    isSystolicError: Boolean,
    diastolic: String,
    setDiastolic: (String) -> Unit,
    isDiastolicError: Boolean,
    pulse: String,
    setPulse: (String) -> Unit,
    isPulseError: Boolean,
    note: String,
    setNote: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    val defModifier = Modifier
        .padding(bottom = 15.dp)

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ItemNewRecord(
            modifier = defModifier,
            value = systolic,
            isError = isSystolicError,
            onValueChange = setSystolic,
            title = "Cистолическое",
            iconId = io.pressurediary.android.common.R.drawable.ic_systolic,
            testTag = "SystolicInput"
        )
        ItemNewRecord(
            modifier = defModifier,
            value = diastolic,
            onValueChange = setDiastolic,
            isError = isDiastolicError,
            title = "Диастолическое",
            iconId = io.pressurediary.android.common.R.drawable.ic_diastolic,
            testTag = "DiastolicInput"
        )
        ItemNewRecord(
            modifier = defModifier,
            value = pulse,
            onValueChange = setPulse,
            title = stringResource(R.string.new_record_pulse),
            isError = isPulseError,
            iconId = io.pressurediary.android.common.R.drawable.ic_heart,
            testTag = "PulseInput"
        )
        ItemNewRecord(
            modifier = defModifier,
            value = note,
            onValueChange = setNote,
            title = stringResource(R.string.new_record_comment),
            iconId = io.pressurediary.android.common.R.drawable.ic_comment,
            maxLines = 5,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            testTag = "NoteInput"
        )
        PDButton(
            text = stringResource(R.string.btn_save),
            onClick = onSaveClick,
            modifier = Modifier.testTag("SaveButton")
        )
    }
}

@Composable
fun ItemNewRecord(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next
    ),
    isError: Boolean = false,
    maxLines: Int = 1,
    testTag: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    end = 10.dp,
                    top = 14.dp
                )
                .size(28.dp),
            painter = painterResource(id = iconId),
            contentDescription = null
        )
        PDTextField(
            modifier = Modifier
                .width(300.dp)
                .testTag(testTag),
            value = value,
            isError = isError,
            onValueChange = onValueChange,
            title = title,
            placeholder = null,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
        )
    }
}