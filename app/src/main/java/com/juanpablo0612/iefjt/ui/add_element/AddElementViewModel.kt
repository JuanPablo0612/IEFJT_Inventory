package com.juanpablo0612.iefjt.ui.add_element

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.iefjt.domain.model.Brand
import com.juanpablo0612.iefjt.domain.model.Status
import com.juanpablo0612.iefjt.domain.model.Type
import com.juanpablo0612.iefjt.domain.usecase.GetBrandsUseCase
import com.juanpablo0612.iefjt.domain.usecase.GetStatusesUseCase
import com.juanpablo0612.iefjt.domain.usecase.GetTypesUseCase
import com.juanpablo0612.iefjt.domain.usecase.SaveElementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddElementViewModel @Inject constructor(
    private val getStatusesUseCase: GetStatusesUseCase,
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getTypesUseCase: GetTypesUseCase,
    private val saveElementUseCase: SaveElementUseCase
) : ViewModel() {
    var uiState by mutableStateOf(AddElementUiState())
        private set

    init {
        getAll()
    }

    private fun getAll() {
        viewModelScope.launch {
            combine(
                getBrandsUseCase(),
                getTypesUseCase(),
                getStatusesUseCase()
            ) { brands, types, statuses ->
                uiState.copy(
                    brands = brands,
                    types = types,
                    statuses = statuses
                )
            }.catch {
                uiState = uiState.copy(exception = it as Exception)
            }.collect {
                uiState = it
            }
        }
    }

    fun onNameInput(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun onSerialInput(serial: String) {
        uiState = uiState.copy(serial = serial)
    }

    fun onLocationInput(location: String) {
        uiState = uiState.copy(location = location)
    }

    fun onStatusSelected(status: Status) {
        uiState = uiState.copy(selectedStatus = status)
    }

    fun onBrandSelected(brand: Brand) {
        uiState = uiState.copy(selectedBrand = brand)
    }

    fun onTypeSelected(type: Type) {
        uiState = uiState.copy(selectedType = type)
    }

    private fun validateFields() {
        val nameValid = uiState.name.isNotBlank()
        val serialValid = true
        val locationValid = uiState.location.isNotBlank()
        val selectedStatusValid = uiState.selectedStatus != null
        val selectedBrandValid = uiState.selectedBrand != null
        val selectedTypeValid = uiState.selectedType != null

        uiState = uiState.copy(
            nameValid = nameValid,
            serialValid = serialValid,
            locationValid = locationValid,
            selectedStatusValid = selectedStatusValid,
            selectedBrandValid = selectedBrandValid,
            selectedTypeValid = selectedTypeValid
        )
    }

    fun onSaveElement() {
        viewModelScope.launch {
            validateFields()

            val allFieldsValid =
                uiState.nameValid && uiState.serialValid && uiState.locationValid && uiState.selectedStatusValid && uiState.selectedBrandValid && uiState.selectedTypeValid

            if (allFieldsValid) {
                uiState = uiState.copy(isLoading = true)

                saveElementUseCase(
                    name = uiState.name.trim(),
                    serial = uiState.serial.trim(),
                    location = uiState.location.trim(),
                    status = uiState.selectedStatus!!,
                    brand = uiState.selectedBrand!!,
                    type = uiState.selectedType!!
                ).fold(
                    onSuccess = {
                        uiState = uiState.copy(isLoading = false, successAddElement = true)
                    },
                    onFailure = {
                        uiState = uiState.copy(isLoading = false, exception = it as Exception)
                    }
                )
            }
        }
    }
}

data class AddElementUiState(
    val isLoading: Boolean = false,
    val successAddElement: Boolean = false,
    val name: String = "",
    val nameValid: Boolean = true,
    val serial: String = "",
    val serialValid: Boolean = true,
    val location: String = "",
    val locationValid: Boolean = true,
    val selectedStatus: Status? = null,
    val selectedStatusValid: Boolean = true,
    val selectedBrand: Brand? = null,
    val selectedBrandValid: Boolean = true,
    val selectedType: Type? = null,
    val selectedTypeValid: Boolean = true,
    val statuses: List<Status> = emptyList(),
    val brands: List<Brand> = emptyList(),
    val types: List<Type> = emptyList(),
    val exception: Exception? = null
)