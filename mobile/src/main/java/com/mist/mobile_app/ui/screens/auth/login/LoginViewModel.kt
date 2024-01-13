package com.mist.mobile_app.ui.screens.auth.login

import android.util.Patterns
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.api.models.LoginModel
import com.mist.common.data.repository.UserRepository
import com.mist.common.data.stores.impl.TokensDataStore
import com.mist.common.utils.BaseViewModel
import com.mist.common.utils.errorflow.NetworkErrorFlow
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Immutable
data class LoginState(
    val isLoadingLogin: Boolean = false,
    val loginModel: LoginModel = LoginModel(
        email = "",
        password = ""
    ),
    val isLoginError: Boolean = false,
    val isPasswordError: Boolean = false,
    val onSuccessfulLoginEvent: StateEvent = consumed
) {
    val isLoginButtonEnabled: Boolean
        get() = loginModel.email.isNotEmpty() && loginModel.password.isNotEmpty()

    /**
     * Validate login
     * Login это email
     */
    fun validateLogin(): Boolean = with(loginModel) {
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validate password
     * Пароль должен содержать:
     *
     * латинские и/или кириллические буквы
     * цифры
     * специальные символы ~ ! ? @ # $ % ^ & * _ - + ( ) [ ] { } > < / \ | " ' . , : ;
     * одну заглавную букву
     * одну строчную буквы
     * не менее 6 и не более 15 символов
     */
    fun validatePassword(): Boolean = with(loginModel) {
        val lengthRequirement = password.length in 6..15
        val upperCaseLetterRequirement = password.any { it.isUpperCase() }
        val lowerCaseLetterRequirement = password.any { it.isLowerCase() }
        val digitRequirement = password.any { it.isDigit() }
        val specialCharacterRequirement = password.any { it in "~!@#$%^&*()_+{}|:\"<>,.?;'[]\\/-" }

        lengthRequirement &&
        upperCaseLetterRequirement &&
        lowerCaseLetterRequirement &&
        digitRequirement &&
        specialCharacterRequirement
    }
}

class LoginViewModel(
    private val tokensDataStore: TokensDataStore,
    private val userRepository: UserRepository
) : BaseViewModel<LoginState>() {

    override val initialState = LoginState()

    fun onLogin() {
        viewModelScope.launch {
            val (
                validateLogin, validatePassword
            ) = state.validateLogin() to state.validatePassword()

            when {
                validateLogin && validatePassword ->
                    login(loginModel = state.loginModel)

                else -> {
                    state = if (!validateLogin) {
                        state.copy(
                            isLoginError = true
                        )
                    } else {
                        state.copy(
                            isPasswordError = true
                        )
                    }
                }
            }
        }
    }

    private suspend fun login(
        loginModel: LoginModel
    ) {
        state = state.copy(
            isLoadingLogin = true
        )
        userRepository.login(
            model = loginModel
        ).fold(
            ifLeft = { error ->
                NetworkErrorFlow.pushError(error)
            },
            ifRight = { tokensModel ->
                tokensDataStore.updateDataStore(
                    data = tokensModel,
                    updateTime = LocalDateTime.now()
                )
                onTriggeredSuccessfulLoginEvent()
            }
        )
        state = state.copy(
            isLoadingLogin = false
        )
    }

    fun onLoginChanged(
        login: String
    ) {
        state = state.copy(
            loginModel = state.loginModel.copy(
                email = login
            )
        )
    }

    fun onPasswordChanged(
        password: String
    ) {
        state = state.copy(
            loginModel = state.loginModel.copy(
                password = password
            )
        )
    }

    //region Events
    private fun onTriggeredSuccessfulLoginEvent() {
        state = state.copy(
            onSuccessfulLoginEvent = triggered
        )
    }

    fun onConsumedSuccessfulLoginEvent() {
        state = state.copy(
            onSuccessfulLoginEvent = consumed
        )
    }
    //endregion
}