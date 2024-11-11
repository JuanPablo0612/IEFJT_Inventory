package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.elements.repository.ElementsRepository
import javax.inject.Inject

class GetElementsByTypeUseCase @Inject constructor(private val elementsRepository: ElementsRepository) {
    operator fun invoke(typeId: String) = elementsRepository.getElementsByType(typeId)
}