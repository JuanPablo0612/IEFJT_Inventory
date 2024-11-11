package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.elements.repository.ElementsRepository
import javax.inject.Inject

class GetElementByIdUseCase @Inject constructor(private val elementsRepository: ElementsRepository) {
    operator fun invoke(elementId: String) =
        elementsRepository.getElementById(elementId = elementId)
}