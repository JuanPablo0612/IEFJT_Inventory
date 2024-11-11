package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.elements.repository.ElementsRepository
import javax.inject.Inject

class GetLastMonthUpdatedElementsUseCase @Inject constructor(private val elementsRepository: ElementsRepository) {
    operator fun invoke() = elementsRepository.getLastMonthUpdatedElements()
}