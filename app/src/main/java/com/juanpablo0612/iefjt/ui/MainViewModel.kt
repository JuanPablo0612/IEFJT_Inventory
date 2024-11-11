package com.juanpablo0612.iefjt.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.juanpablo0612.iefjt.domain.usecase.GetIsUserLoggedInUseCase
import com.juanpablo0612.iefjt.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getIsUserLoggedInUseCase: GetIsUserLoggedInUseCase,
    private val logoutUseCase: LogoutUseCase
) :
    ViewModel() {
    var uiState by mutableStateOf(MainUiState())
        private set

    init {
        getIsLoggedIn()
    }

    private fun getIsLoggedIn() {
        getIsUserLoggedInUseCase().fold(
            onSuccess = {
                uiState = uiState.copy(isLoading = false, isLoggedIn = it)
            },
            onFailure = {
                uiState = uiState.copy(isLoading = false, isLoggedIn = false)
            }
        )
    }

    fun onLogout() {
        logoutUseCase().fold(
            onSuccess = {
                uiState = uiState.copy(isLoggedIn = false, loggedOut = true)
            },
            onFailure = {
                uiState = uiState.copy(isLoggedIn = true)
            }
        )
    }
}

data class MainUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val loggedOut: Boolean = false
)