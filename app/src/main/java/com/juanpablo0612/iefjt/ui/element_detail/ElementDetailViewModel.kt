package com.juanpablo0612.iefjt.ui.element_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.juanpablo0612.iefjt.domain.model.Element
import com.juanpablo0612.iefjt.domain.usecase.GetElementByIdUseCase
import com.juanpablo0612.iefjt.ui.navigation.ElementDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElementDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getElementByIdUseCase: GetElementByIdUseCase
) : ViewModel() {
    private val elementDetail = savedStateHandle.toRoute<ElementDetail>()

    var uiState by mutableStateOf(ElementDetailUiState())
        private set

    init {
        getElement()
    }

    private fun getElement() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            getElementByIdUseCase(elementDetail.id).catch {
                uiState = uiState.copy(isLoading = false, exception = it as Exception)
            }.collect { element ->
                uiState=uiState.copy(isLoading = false, element = element)
            }
        }
    }
}

data class ElementDetailUiState(
    val isLoading: Boolean = false,
    val element: Element? = null,
    val exception: Exception? = null
)