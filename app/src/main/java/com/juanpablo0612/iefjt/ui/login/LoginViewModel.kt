package com.juanpablo0612.iefjt.ui.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.iefjt.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailInput(email: String) {
        uiState = uiState.copy(email = email.trim())
    }

    fun onPasswordInput(password: String) {
        uiState = uiState.copy(password = password.trim())
    }

    fun onPasswordVisibilityChange() {
        uiState = uiState.copy(passwordVisible = !uiState.passwordVisible)
    }

    private fun validateFields() {
        val emailPattern = Patterns.EMAIL_ADDRESS
        val isEmailValid = emailPattern.matcher(uiState.email).matches()
        val isPasswordValid = uiState.password.length >= 6

        uiState = uiState.copy(emailValid = isEmailValid, passwordValid = isPasswordValid)
    }

    fun onLogin() {
        viewModelScope.launch {
            validateFields()
            if (uiState.emailValid && uiState.passwordValid) {
                uiState = uiState.copy(isLoading = true, exception = null)

                loginUseCase(email = uiState.email, password = uiState.password).fold(
                    onSuccess = {
                        uiState = uiState.copy(isLoading = false, successLogin = true)
                    },
                    onFailure = {
                        uiState = uiState.copy(isLoading = false, exception = it as Exception)
                    }
                )
            }
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val emailValid: Boolean = true,
    val password: String = "",
    val passwordValid: Boolean = true,
    val passwordVisible: Boolean = false,
    val successLogin: Boolean = false,
    val exception: Exception? = null
)