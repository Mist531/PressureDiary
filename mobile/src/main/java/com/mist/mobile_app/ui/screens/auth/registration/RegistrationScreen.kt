package com.mist.mobile_app.ui.screens.auth.registration

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.api.models.Gender
import com.mist.mobile_app.ui.components.PDButton
import com.mist.mobile_app.ui.components.PDLoader
import com.mist.mobile_app.ui.components.PDPasswordTextField
import com.mist.mobile_app.ui.components.PDTextField
import com.mist.mobile_app.ui.theme.PDTheme
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    onGoToLogin: () -> Unit = {},
    viewModel: RegistrationViewModel = getViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EventEffect(
        event = state.onSuccessfulRegistrationEvent,
        onConsumed = remember {
            viewModel::onConsumedSuccessfulRegistrationEvent
        },
        action = onGoToLogin
    )

    Box(
        modifier = modifier
            .imePadding()
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = state.isLoadingRegistration,
            label = ""
        ) { bool ->
            when (bool) {
                true -> {
                    PDLoader()
                }

                false -> {
                    RegistrationScreenContent(
                        email = state.email,
                        password = state.password,
                        firstName = state.firstName,
                        lastName = state.lastName,
                        gender = state.gender,
                        dateOfBirth = state.dateOfBirth,
                        onEmailChanged = viewModel::onEmailChanged,
                        onPasswordChanged = viewModel::onPasswordChanged,
                        onFirstNameChanged = viewModel::onFirstNameChanged,
                        onLastNameChanged = viewModel::onLastNameChanged,
                        onDateOfBirthChanged = viewModel::onDateOfBirthChanged,
                        onGenderChanged = viewModel::onGenderChanged,
                        onRegistrationClicked = viewModel::onRegistration,
                        onGoToLogin = onGoToLogin,
                        isEmailError = state.isEmailError,
                        isPasswordError = state.isPasswordError,
                        isFirstNameError = state.isFirstNameError
                    )
                }
            }
        }
    }
}

@Composable
fun RegistrationScreenContent(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    lastName: String?,
    firstName: String,
    gender: Gender,
    dateOfBirth: LocalDate,
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onFirstNameChanged: (String) -> Unit = {},
    onLastNameChanged: (String) -> Unit = {},
    onDateOfBirthChanged: (LocalDate) -> Unit = {},
    onGenderChanged: (Gender) -> Unit = {},
    onRegistrationClicked: () -> Unit = {},
    onGoToLogin: () -> Unit = {},
    isEmailError: Boolean = false,
    isPasswordError: Boolean = false,
    isFirstNameError: Boolean = false,
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        PDTextField(
            modifier = Modifier,
            value = firstName,
            onValueChange = remember { onFirstNameChanged },
            title = "Имя*",
            placeholder = "Введите имя",
            isError = isFirstNameError,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
        )
        PDTextField(
            modifier = Modifier,
            value = remember(lastName) {
                lastName ?: ""
            },
            onValueChange = remember { onLastNameChanged },
            title = "Фамилия",
            placeholder = "Введите фамилию",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
        )
        PDTextField(
            modifier = Modifier,
            value = email,
            onValueChange = remember { onEmailChanged },
            title = "Email*",
            placeholder = "Введите email",
            isError = isEmailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
        )
        PDPasswordTextField(
            modifier = Modifier,
            value = password,
            onValueChange = remember { onPasswordChanged },
            title = "Password*",
            placeholder = "Введите password",
            isError = isPasswordError,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = {
                    onRegistrationClicked()
                }
            )
        )

        //TODO: select gender

        //TODO: add date picker

        PDButton(
            modifier = Modifier,
            text = "Зарегистрироваться",
            onClick = remember { onRegistrationClicked },
        )
        PDButton(
            modifier = Modifier,
            text = "Вернуться к авторизации",
            onClick = remember { onGoToLogin },
        )
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    PDTheme {
        RegistrationScreen()
    }
}