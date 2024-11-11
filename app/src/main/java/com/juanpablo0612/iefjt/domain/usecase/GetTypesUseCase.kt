package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.types.repository.TypesRepository
import javax.inject.Inject

class GetTypesUseCase @Inject constructor(private val typesRepository: TypesRepository) {
    operator fun invoke() = typesRepository.getTypes()
}