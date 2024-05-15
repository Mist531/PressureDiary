package com.mist.mobile_app.ui.screens.auth.login

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mist.mobile_app.ui.components.PDButton
import com.mist.mobile_app.ui.components.PDCircularLoader
import com.mist.mobile_app.ui.components.PDPasswordTextField
import com.mist.mobile_app.ui.components.PDTextField
import com.mist.mobile_app.ui.theme.PDTheme
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onGoToRegistration: () -> Unit = {},
    onGoToMainScreen: () -> Unit = {},
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EventEffect(
        event = state.onSuccessfulLoginEvent,
        onConsumed = remember {
            viewModel::onConsumedSuccessfulLoginEvent
        },
        action = onGoToMainScreen
    )

    Box(
        modifier = modifier
            .imePadding()
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = state.isLoadingLogin,
            label = ""
        ) { bool ->
            when (bool) {
                true -> {
                    PDCircularLoader()
                }

                false -> {
                    LoginScreenContent(
                        modifier = Modifier,
                        login = state.loginModel.email,
                        password = state.loginModel.password,
                        onLoginChanged = viewModel::onLoginChanged,
                        onPasswordChanged = viewModel::onPasswordChanged,
                        onLoginClicked = viewModel::onLogin,
                        loginButtonEnabled = state.isLoginButtonEnabled,
                        onGoToRegistration = onGoToRegistration,
                        isEmailError = state.isEmailError
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    login: String,
    password: String,
    loginButtonEnabled: Boolean = true,
    onLoginChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLoginClicked: () -> Unit = {},
    onGoToRegistration: () -> Unit = {},
    isEmailError: Boolean = false
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
            value = login,
            onValueChange = remember { onLoginChanged },
            title = "Email",
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
            title = "Password",
            placeholder = "Введите password",
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = {
                    onLoginClicked()
                }
            )
        )
        PDButton(
            modifier = Modifier,
            text = "Войти",
            onClick = remember { onLoginClicked },
            enabled = loginButtonEnabled
        )
        PDButton(
            modifier = Modifier,
            text = "Зарегистрироваться",
            onClick = remember { onGoToRegistration },
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    PDTheme {
        LoginScreen()
    }
}

