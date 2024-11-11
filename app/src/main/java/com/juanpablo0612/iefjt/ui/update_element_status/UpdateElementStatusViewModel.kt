package com.juanpablo0612.iefjt.ui.update_element_status

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.juanpablo0612.iefjt.domain.model.Status
import com.juanpablo0612.iefjt.domain.usecase.GetStatusesUseCase
import com.juanpablo0612.iefjt.domain.usecase.UpdateElementStatusUseCase
import com.juanpablo0612.iefjt.ui.navigation.UpdateElementStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateElementStatusViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateElementStatusUseCase: UpdateElementStatusUseCase,
    private val getStatusesUseCase: GetStatusesUseCase
) : ViewModel() {
    private val updateElementStatus = savedStateHandle.toRoute<UpdateElementStatus>()

    var uiState by mutableStateOf(UpdateElementStatusUiState())
        private set

    init {
        getStatuses()
    }

    private fun getStatuses() {
        viewModelScope.launch {
            getStatusesUseCase().catch {
                uiState = uiState.copy(exception = it as Exception)
            }.collect { statuses ->
                uiState = uiState.copy(
                    statuses = statuses,
                    currentStatus = statuses.find { it.id == updateElementStatus.currentStatusId }
                )
            }
        }
    }

    fun onStatusSelected(status: Status) {
        uiState = uiState.copy(selectedStatus = status)
    }

    fun onUpdateStatus() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            if (uiState.selectedStatus == null) return@launch

            if (uiState.selectedStatus!!.id == uiState.currentStatus!!.id) {
                uiState = uiState.copy(isLoading = false, successUpdateElementStatus = true)
                return@launch
            }

            updateElementStatusUseCase(
                updateElementStatus.elementId,
                uiState.selectedStatus!!
            ).fold(
                onSuccess = {
                    uiState = uiState.copy(isLoading = false, successUpdateElementStatus = true)
                },
                onFailure = {
                    uiState = uiState.copy(exception = it as Exception)
                }
            )
        }
    }
}

data class UpdateElementStatusUiState(
    val isLoading: Boolean = false,
    val successUpdateElementStatus: Boolean = false,
    val statuses: List<Status> = emptyList(),
    val currentStatus: Status? = null,
    val selectedStatus: Status? = null,
    val exception: Exception? = null
)