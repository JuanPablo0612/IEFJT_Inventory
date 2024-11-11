package com.juanpablo0612.iefjt.ui.add_type

import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.iefjt.domain.usecase.SaveTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTypeViewModel @Inject constructor(private val saveTypeUseCase: SaveTypeUseCase) :
    ViewModel() {
    var uiState by mutableStateOf(AddTypeUiState())
        private set

    fun onNameInput(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun onImageUrlInput(imageUrl: String) {
        uiState = uiState.copy(imageUrl = imageUrl)
    }

    fun onImageUriSelected(imageUri: Uri?) {
        uiState = uiState.copy(imageUri = imageUri)
    }

    fun onUploadImageSelectedChange(selected: Boolean) {
        uiState = uiState.copy(uploadImageSelected = selected)
    }

    private fun validateFields() {
        val nameValid = uiState.name.isNotBlank()
        val imageUrlValid =
            if (uiState.uploadImageSelected) true else Patterns.WEB_URL.matcher(uiState.imageUrl)
                .matches()
        val imageUriValid = if (uiState.uploadImageSelected) uiState.imageUri != null else true

        uiState = uiState.copy(
            nameValid = nameValid,
            imageUrlValid = imageUrlValid,
            imageUriValid = imageUriValid
        )
    }

    fun onSaveType() {
        viewModelScope.launch {
            validateFields()
            if (uiState.nameValid && uiState.imageUrlValid && uiState.imageUriValid) {
                uiState = uiState.copy(isLoading = true)

                saveTypeUseCase(uiState.name.trim(), uiState.imageUrl.trim(), uiState.imageUri).fold(
                    onSuccess = {
                        uiState = uiState.copy(isLoading = false, successAddBrand = true)
                    },
                    onFailure = {
                        uiState = uiState.copy(isLoading = false, exception = it as Exception)
                    }
                )
            }
        }
    }
}

data class AddTypeUiState(
    val isLoading: Boolean = false,
    val successAddBrand: Boolean = false,
    val name: String = "",
    val nameValid: Boolean = true,
    val imageUrl: String = "",
    val imageUrlValid: Boolean = true,
    val imageUri: Uri? = null,
    val imageUriValid: Boolean = true,
    val uploadImageSelected: Boolean = true,
    val exception: Exception? = null
)