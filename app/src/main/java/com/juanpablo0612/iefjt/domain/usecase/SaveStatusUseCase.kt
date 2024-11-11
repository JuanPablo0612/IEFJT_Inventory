package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.statuses.repository.StatusesRepository
import com.juanpablo0612.iefjt.domain.model.Status
import javax.inject.Inject

class SaveStatusUseCase @Inject constructor(private val statusesRepository: StatusesRepository) {
    suspend operator fun invoke(name: String, color: String): Result<Unit> {
        val status = Status(
            name = name,
            color = color
        )

        return statusesRepository.saveStatus(status)
    }
}