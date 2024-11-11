package com.juanpablo0612.iefjt.ui.add_status

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.iefjt.domain.usecase.SaveStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStatusViewModel @Inject constructor(private val saveStatusUseCase: SaveStatusUseCase) :
    ViewModel() {
    var uiState by mutableStateOf(AddStatusUiState())
        private set

    fun onNameInput(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun onColorHexChange(colorHex: String) {
        uiState = uiState.copy(colorHex = colorHex)
    }

    private fun validateFields() {
        val nameValid = uiState.name.isNotBlank()
        val colorHexValid = uiState.colorHex.isNotBlank()

        uiState = uiState.copy(nameValid = nameValid, colorHexValid = colorHexValid)
    }

    fun onSaveStatus() {
        viewModelScope.launch {
            validateFields()
            if (uiState.nameValid && uiState.colorHexValid) {
                uiState = uiState.copy(isLoading = true)

                saveStatusUseCase(uiState.name.trim(), uiState.colorHex.trim()).fold(
                    onSuccess = {
                        uiState = uiState.copy(isLoading = false, successAddStatus = true)
                    },
                    onFailure = {
                        uiState = uiState.copy(isLoading = false, exception = it as Exception)
                    }
                )
            }
        }
    }
}

data class AddStatusUiState(
    val isLoading: Boolean = false,
    val successAddStatus: Boolean = false,
    val name: String = "",
    val nameValid: Boolean = true,
    val colorHex: String = "ffffff",
    val colorHexValid: Boolean = true,
    val exception: Exception? = null
)