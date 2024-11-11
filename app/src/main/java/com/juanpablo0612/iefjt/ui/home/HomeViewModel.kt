package com.juanpablo0612.iefjt.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.iefjt.domain.model.Brand
import com.juanpablo0612.iefjt.domain.model.Element
import com.juanpablo0612.iefjt.domain.model.Status
import com.juanpablo0612.iefjt.domain.model.Type
import com.juanpablo0612.iefjt.domain.usecase.GetBrandsUseCase
import com.juanpablo0612.iefjt.domain.usecase.GetLastMonthUpdatedElementsUseCase
import com.juanpablo0612.iefjt.domain.usecase.GetStatusesUseCase
import com.juanpablo0612.iefjt.domain.usecase.GetTypesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLastMonthUpdatedElementsUseCase: GetLastMonthUpdatedElementsUseCase,
    private val getStatusesUseCase: GetStatusesUseCase,
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getTypesUseCase: GetTypesUseCase
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        getAll()
    }

    private fun getAll() {
        viewModelScope.launch {
            combine(
                getLastMonthUpdatedElementsUseCase(),
                getBrandsUseCase(),
                getTypesUseCase(),
                getStatusesUseCase()
            ) { elements, brands, types, statuses ->
                uiState.copy(
                    lastMonthUpdatedElements = elements,
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
}

data class HomeUiState(
    val loading: Boolean = false,
    val lastMonthUpdatedElements: List<Element> = emptyList(),
    val statuses: List<Status> = emptyList(),
    val brands: List<Brand> = emptyList(),
    val types: List<Type> = emptyList(),
    val exception: Exception? = null
)