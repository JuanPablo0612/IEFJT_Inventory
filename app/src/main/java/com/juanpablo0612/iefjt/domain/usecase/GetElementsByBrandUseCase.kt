package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.elements.repository.ElementsRepository
import javax.inject.Inject

class GetElementsByBrandUseCase @Inject constructor(private val elementsRepository: ElementsRepository) {
    operator fun invoke(brandId: String) = elementsRepository.getElementsByBrand(brandId)
}