package com.juanpablo0612.iefjt.ui.register

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.iefjt.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun onNameInput(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun onEmailInput(email: String) {
        uiState = uiState.copy(email = email.trim())
    }

    fun onPasswordInput(password: String) {
        uiState = uiState.copy(password = password.trim())
    }

    fun onPasswordConfirmInput(passwordConfirm: String) {
        uiState = uiState.copy(passwordConfirm = passwordConfirm.trim())
    }

    fun onPasswordVisibilityChange() {
        uiState = uiState.copy(passwordVisible = !uiState.passwordVisible)
    }

    private fun validateFields() {
        val isNameValid = uiState.name.trim().length >= 3
        val emailPattern = Patterns.EMAIL_ADDRESS
        val isEmailValid = emailPattern.matcher(uiState.email).matches()
        val isPasswordValid = uiState.password.length >= 6
        val isPasswordConfirmValid = uiState.password == uiState.passwordConfirm

        uiState = uiState.copy(
            nameValid = isNameValid,
            emailValid = isEmailValid,
            passwordValid = isPasswordValid,
            passwordConfirmValid = isPasswordConfirmValid,
        )
    }

    fun onRegister() {
        viewModelScope.launch {
            validateFields()

            if (uiState.nameValid && uiState.emailValid && uiState.passwordValid && uiState.passwordConfirmValid) {
                uiState = uiState.copy(isLoading = true, exception = null)

                registerUseCase(
                    name = uiState.name.trim(),
                    email = uiState.email,
                    password = uiState.password
                ).fold(
                    onSuccess = {
                        uiState = uiState.copy(isLoading = false, successRegister = true)
                    },
                    onFailure = {
                        uiState = uiState.copy(isLoading = false, exception = it as Exception)
                    }
                )
            }
        }
    }
}

data class RegisterUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val nameValid: Boolean = true,
    val email: String = "",
    val emailValid: Boolean = true,
    val password: String = "",
    val passwordValid: Boolean = true,
    val passwordConfirm: String = "",
    val passwordConfirmValid: Boolean = true,
    val passwordVisible: Boolean = false,
    val successRegister: Boolean = false,
    val exception: Exception? = null
)