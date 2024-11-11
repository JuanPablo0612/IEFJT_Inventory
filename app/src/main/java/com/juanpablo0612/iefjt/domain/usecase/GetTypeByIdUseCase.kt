package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.types.repository.TypesRepository
import javax.inject.Inject

class GetTypeByIdUseCase @Inject constructor(private val typesRepository: TypesRepository) {
    suspend operator fun invoke(typeId: String) = typesRepository.getTypeById(typeId)
}