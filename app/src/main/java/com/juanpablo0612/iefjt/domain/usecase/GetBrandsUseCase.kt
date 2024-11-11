package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.brands.repository.BrandsRepository
import javax.inject.Inject

class GetBrandsUseCase @Inject constructor(private val brandsRepository: BrandsRepository) {
    operator fun invoke() = brandsRepository.getBrands()
}