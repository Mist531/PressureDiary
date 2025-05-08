package io.pressurediary.android.mobile.ui.screens.auth.registration

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.palm.composestateevents.EventEffect
import io.pressurediary.android.common.ui.PDColors
import io.pressurediary.android.mobile.ui.components.PDButton
import io.pressurediary.android.mobile.ui.components.PDCircularLoader
import io.pressurediary.android.mobile.ui.components.PDPasswordTextField
import io.pressurediary.android.mobile.ui.components.PDTextField
import io.pressurediary.android.mobile.ui.theme.PDTheme
import io.pressurediary.server.api.models.Gender
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    onGoToLogin: () -> Unit = {},
    viewModel: RegistrationViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EventEffect(
        event = state.onSuccessfulRegistrationEvent,
        onConsumed = viewModel::onConsumedSuccessfulRegistrationEvent,
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
                true -> PDCircularLoader()

                false -> RegistrationScreenContent(
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

@Composable
fun RegistrationScreenContent(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    lastName: String?,
    firstName: String,
    gender: Gender,
    dateOfBirth: LocalDate,
    isEmailError: Boolean = false,
    isPasswordError: Boolean = false,
    isFirstNameError: Boolean = false,
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onFirstNameChanged: (String) -> Unit = {},
    onLastNameChanged: (String) -> Unit = {},
    onDateOfBirthChanged: (LocalDate) -> Unit = {},
    onGenderChanged: (Gender) -> Unit = {},
    onRegistrationClicked: () -> Unit = {},
    onGoToLogin: () -> Unit = {},
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
            onValueChange = onFirstNameChanged,
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
            value = lastName ?: "",
            onValueChange = onLastNameChanged,
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
            onValueChange = onEmailChanged,
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
            onValueChange = onPasswordChanged,
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

        AnimatedVisibility(visible = isPasswordError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = """
                Пароль должен содержать:
                     Латинские буквы
                     Цифры
                     Специальные символы
                     Одну заглавную букву
                     Одну строчную буквы
                     Не менее 6 и не более 15 символов
            """.trimIndent(),
                textAlign = TextAlign.Start,
                color = PDColors.black,
            )
        }

        //TODO: select gender
        //TODO нормы возратной групппы

        //TODO: add date picker

        PDButton(
            modifier = Modifier,
            text = "Зарегистрироваться",
            onClick = onRegistrationClicked,
        )
        PDButton(
            modifier = Modifier,
            text = "Вернуться к авторизации",
            onClick = onGoToLogin,
        )
    }
}

@Preview
@Composable
private fun RegistrationScreenPreview() {
    PDTheme {
        RegistrationScreen()
    }
}