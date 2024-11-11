package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.elements.repository.ElementsRepository
import javax.inject.Inject

class GetElementsByStatusUseCase @Inject constructor(private val elementsRepository: ElementsRepository) {
    operator fun invoke(statusId: String) = elementsRepository.getElementsByStatus(statusId = statusId)
}