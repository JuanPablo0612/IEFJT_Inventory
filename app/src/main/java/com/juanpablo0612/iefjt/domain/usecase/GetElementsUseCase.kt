package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.elements.repository.ElementsRepository
import javax.inject.Inject

class GetElementsUseCase @Inject constructor(private val elementsRepository: ElementsRepository) {
    suspend operator fun invoke() = elementsRepository.getElements()
}