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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Immutable
data class LoginState(
    val isLoadingLogin: Boolean = true,
    val loginModel: LoginModel = LoginModel(
        email = "",
        password = ""
    ),
    val isEmailError: Boolean = false,
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
     */
    fun validatePassword(): Boolean = loginModel.password.length >= 6
}

class LoginViewModel(
    private val tokensDataStore: TokensDataStore,
    private val userRepository: UserRepository
) : BaseViewModel<LoginState>() {

    override val initialState = LoginState()

    init {
        viewModelScope.launch {
            tokensDataStore.getStateFlow().firstOrNull().let { tokens ->
                if (tokens == null) {
                    state = state.copy(
                        isLoadingLogin = false
                    )
                } else {
                    tokensDataStore
                }
            }
        }
    }

    fun onLogin() {
        viewModelScope.launch {
            val (
                validateLogin, validatePassword
            ) = state.validateLogin() to state.validatePassword()

            when {
                validateLogin && validatePassword ->
                    login(loginModel = state.loginModel)

                else -> {
                    state = state.copy(
                        isEmailError = !validateLogin,
                        isPasswordError = !validatePassword,
                    )
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