package com.mist.mobile_app.ui.screens.auth.registration

import android.util.Patterns
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.api.models.Gender
import com.example.api.models.PostUserRequestModel
import com.mist.common.data.repository.UserRepository
import com.mist.common.utils.BaseViewModel
import com.mist.common.utils.errorflow.NetworkErrorFlow
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.TimeZone

@Immutable
data class RegistrationState(
    val isLoadingRegistration: Boolean = false,
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String? = null,
    val dateOfBirth: LocalDate = LocalDate.now(),
    val gender: Gender = Gender.O,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isFirstNameError: Boolean = false,
    val onSuccessfulRegistrationEvent: StateEvent = consumed
) {
    fun getPostUserRequestModel(): PostUserRequestModel =
        PostUserRequestModel(
            email = email,
            password = password,
            firstName = firstName,
            lastName = lastName,
            dateOfBirth = dateOfBirth,
            gender = gender,
            timeZone = TimeZone.getDefault().id
        )

    val validateFirstName
        get() = firstName.isNotEmpty()

    fun validateEmail(): Boolean =
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

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
    fun validatePassword(): Boolean {
        val lengthRequirement = password.length in 6..15
        val upperCaseLetterRequirement = password.any { it.isUpperCase() }
        val lowerCaseLetterRequirement = password.any { it.isLowerCase() }
        val digitRequirement = password.any { it.isDigit() }
        val specialCharacterRequirement = password.any { it in "~!@#$%^&*()_+{}|:\"<>,.?;'[]\\/-" }

        return lengthRequirement &&
        upperCaseLetterRequirement &&
        lowerCaseLetterRequirement &&
        digitRequirement &&
        specialCharacterRequirement
    }
}

class RegistrationViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<RegistrationState>() {

    override val initialState = RegistrationState()

    fun onRegistration() {
        viewModelScope.launch {
            val (
                validateEmail, validatePassword,
            ) = state.validateEmail() to state.validatePassword()

            val validateFirstName = state.validateFirstName


            when {
                validateEmail && validatePassword && validateFirstName ->
                    registration(registrationModel = state.getPostUserRequestModel())

                else -> {
                    state = state.copy(
                        isEmailError = !validateEmail,
                        isPasswordError = !validatePassword,
                        isFirstNameError = !validateFirstName
                    )
                }
            }
        }
    }

    private suspend fun registration(
        registrationModel: PostUserRequestModel
    ) {
        state = state.copy(
            isLoadingRegistration = true
        )
        userRepository.postUser(
            model = registrationModel
        ).fold(
            ifLeft = { error ->
                NetworkErrorFlow.pushError(error)
            },
            ifRight = {
                onTriggeredSuccessfulRegistrationEvent()
            }
        )
        state = state.copy(
            isLoadingRegistration = false
        )
    }

    //region Changed
    fun onEmailChanged(
        email: String
    ) {
        state = state.copy(
            email = email
        )
    }

    fun onPasswordChanged(
        password: String
    ) {
        state = state.copy(
            password = password
        )
    }

    fun onFirstNameChanged(
        firstName: String
    ) {
        state = state.copy(
            firstName = firstName
        )
    }

    fun onLastNameChanged(
        lastName: String
    ) {
        state = state.copy(
            lastName = lastName
        )
    }

    fun onDateOfBirthChanged(
        dateOfBirth: LocalDate
    ) {
        state = state.copy(
            dateOfBirth = dateOfBirth
        )
    }

    fun onGenderChanged(
        gender: Gender
    ) {
        state = state.copy(
            gender = gender
        )
    }
    //endregion

    //region Events
    private fun onTriggeredSuccessfulRegistrationEvent() {
        state = state.copy(
            onSuccessfulRegistrationEvent = triggered
        )
    }

    fun onConsumedSuccessfulRegistrationEvent() {
        state = state.copy(
            onSuccessfulRegistrationEvent = consumed
        )
    }
    //endregion
}