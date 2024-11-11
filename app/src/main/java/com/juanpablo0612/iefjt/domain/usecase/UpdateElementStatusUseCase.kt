package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.elements.repository.ElementsRepository
import com.juanpablo0612.iefjt.data.exceptions.resultOf
import com.juanpablo0612.iefjt.domain.model.Status
import javax.inject.Inject

class UpdateElementStatusUseCase @Inject constructor(private val elementsRepository: ElementsRepository) {
    suspend operator fun invoke(id: String, status: Status): Result<Unit> {
        return resultOf {
            elementsRepository.updateElementStatus(id, status).getOrThrow()
        }
    }
}