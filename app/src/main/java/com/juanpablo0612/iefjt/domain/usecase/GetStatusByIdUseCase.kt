package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.statuses.repository.StatusesRepository
import javax.inject.Inject

class GetStatusByIdUseCase @Inject constructor(private val statusesRepository: StatusesRepository) {
    suspend operator fun invoke(statusId: String) =
        statusesRepository.getStatusById(statusId = statusId)
}