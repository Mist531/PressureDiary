package com.mist.mobile_app.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mist.common.ui.PDColors
import com.mist.mobile_app.ui.theme.PDTheme

@Composable
fun PDTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    title: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val colorBorder = remember(isError) {
        if (isError) {
            PDColors.Error
        } else {
            PDColors.Grey
        }
    }

    TextField(
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = colorBorder,
                shape = RoundedCornerShape(15.dp)
            ),
        value = value,
        onValueChange = remember {
            onValueChange
        },
        label = title?.let { text ->
            {
                Text(
                    text = text,
                    textAlign = TextAlign.Start,
                    color = PDColors.Black,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        placeholder = placeholder?.let { text ->
            {
                Text(
                    text = text,
                    textAlign = TextAlign.Start,
                    color = PDColors.plGrey,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            errorIndicatorColor = Color.Unspecified,
            focusedIndicatorColor = Color.Unspecified,
            unfocusedIndicatorColor = Color.Unspecified,
            errorCursorColor = PDColors.Error,
            focusedContainerColor = PDColors.White,
            disabledContainerColor = PDColors.White,
            unfocusedContainerColor = PDColors.White,
            errorContainerColor = PDColors.White,
            cursorColor = PDColors.Orange,
        ),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon
    )
}

@Composable
fun PDPasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    title: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Next,
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    PDTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        title = title,
        placeholder = placeholder,
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
            autoCorrect = false,
        ),
        keyboardActions = keyboardActions,
        maxLines = 1,
        visualTransformation = remember(passwordVisible) {
            if (passwordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation()
        },
        trailingIcon = {
            val image = remember(passwordVisible) {
                if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
            }

            IconButton(
                onClick = remember {
                    {
                        passwordVisible = !passwordVisible
                    }
                }
            ) {
                Icon(
                    imageVector = image,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewPDTextField() {
    var text by remember {
        mutableStateOf("")
    }

    val error = remember(text) {
        text.contains("error")
    }

    PDTheme {
        PDTextField(
            modifier = Modifier.padding(10.dp),
            value = text,
            onValueChange = { str ->
                text = str
            },
            isError = error,
            title = "Title",
            placeholder = "Placeholder"
        )
    }
}