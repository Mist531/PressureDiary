package com.mist.mobile_app.ui.screens.main.records

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mist.mobile_app.R
import com.mist.mobile_app.ui.components.PDButton
import com.mist.mobile_app.ui.components.PDTextField

@Composable
fun RecordContent(
    modifier: Modifier = Modifier,
    systolic: String,
    setSystolic: (String) -> Unit,
    diastolic: String,
    setDiastolic: (String) -> Unit,
    pulse: String,
    setPulse: (String) -> Unit,
    note: String,
    setNote: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    val defModifier = Modifier
        .padding(
            bottom = 15.dp
        )

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ItemNewRecord(
            modifier = defModifier,
            value = systolic,
            onValueChange = setSystolic,
            title = "Cистолическое",
            iconId = com.mist.common.R.drawable.ic_systolic,
        )
        ItemNewRecord(
            modifier = defModifier,
            value = diastolic,
            onValueChange = setDiastolic,
            title = "Диастолическое",
            iconId = com.mist.common.R.drawable.ic_diastolic,
        )
        ItemNewRecord(
            modifier = defModifier,
            value = pulse,
            onValueChange = setPulse,
            title = stringResource(R.string.new_record_pulse),
            iconId = com.mist.common.R.drawable.ic_heart
        )
        ItemNewRecord(
            modifier = defModifier,
            value = note,
            onValueChange = setNote,
            title = stringResource(R.string.new_record_comment),
            iconId = com.mist.common.R.drawable.ic_comment,
            maxLines = 5,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        PDButton(

            text = stringResource(R.string.btn_save),
            onClick = onSaveClick
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
    maxLines: Int = 1,
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
                .width(300.dp),
            value = value,
            onValueChange = onValueChange,
            title = title,
            placeholder = null,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
        )
    }
}