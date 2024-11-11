package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.statuses.repository.StatusesRepository
import javax.inject.Inject

class GetStatusesUseCase @Inject constructor(private val statusesRepository: StatusesRepository) {
    operator fun invoke() = statusesRepository.getStatuses()
}