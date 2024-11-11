package com.juanpablo0612.iefjt.ui.element_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.juanpablo0612.iefjt.domain.model.Brand
import com.juanpablo0612.iefjt.domain.model.Element
import com.juanpablo0612.iefjt.domain.model.Status
import com.juanpablo0612.iefjt.domain.model.Type
import com.juanpablo0612.iefjt.domain.usecase.GetBrandByIdUseCase
import com.juanpablo0612.iefjt.domain.usecase.GetElementsByBrandUseCase
import com.juanpablo0612.iefjt.domain.usecase.GetElementsByStatusUseCase
import com.juanpablo0612.iefjt.domain.usecase.GetElementsByTypeUseCase
import com.juanpablo0612.iefjt.domain.usecase.GetStatusByIdUseCase
import com.juanpablo0612.iefjt.domain.usecase.GetTypeByIdUseCase
import com.juanpablo0612.iefjt.ui.navigation.ElementList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElementListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStatusByIdUseCase: GetStatusByIdUseCase,
    private val getTypeByIdUseCase: GetTypeByIdUseCase,
    private val getBrandByIdUseCase: GetBrandByIdUseCase,
    private val getElementsByStatusUseCase: GetElementsByStatusUseCase,
    private val getElementsByTypeUseCase: GetElementsByTypeUseCase,
    private val getElementsByBrandUseCase: GetElementsByBrandUseCase
) : ViewModel() {

    private val elementList = savedStateHandle.toRoute<ElementList>()
    var uiState by mutableStateOf(ElementListUiState())
        private set

    init {
        elementList.statusId?.let { getElementsBy(FilterType.STATUS, it) }
        elementList.typeId?.let { getElementsBy(FilterType.TYPE, it) }
        elementList.brandId?.let { getElementsBy(FilterType.BRAND, it) }
    }

    private fun getElementsBy(type: FilterType, id: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = when (type) {
                FilterType.STATUS -> getStatusByIdUseCase(id)
                FilterType.TYPE -> getTypeByIdUseCase(id)
                FilterType.BRAND -> getBrandByIdUseCase(id)
            }

            result.fold(
                onSuccess = { status ->
                    val elementsFlow = when (type) {
                        FilterType.STATUS -> getElementsByStatusUseCase(id)
                        FilterType.TYPE -> getElementsByTypeUseCase(id)
                        FilterType.BRAND -> getElementsByBrandUseCase(id)
                    }

                    elementsFlow.catch {
                        uiState = uiState.copy(isLoading = false, exception = it as Exception)
                    }.collect { elements ->
                        uiState = uiState.copy(
                            isLoading = false,
                            status = if (type == FilterType.STATUS) status as Status else uiState.status,
                            type = if (type == FilterType.TYPE) status as Type else uiState.type,
                            brand = if (type == FilterType.BRAND) status as Brand else uiState.brand,
                            elements = elements
                        )
                    }
                },
                onFailure = {
                    uiState = uiState.copy(isLoading = false, exception = it as Exception)
                }
            )
        }
    }
}

enum class FilterType {
    STATUS, TYPE, BRAND
}

data class ElementListUiState(
    val isLoading: Boolean = false,
    val exception: Exception? = null,
    val elements: List<Element> = emptyList(),
    val status: Status? = null,
    val type: Type? = null,
    val brand: Brand? = null
)
