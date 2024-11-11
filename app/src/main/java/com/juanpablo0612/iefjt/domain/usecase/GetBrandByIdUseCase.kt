package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.brands.repository.BrandsRepository
import javax.inject.Inject

class GetBrandByIdUseCase @Inject constructor(private val brandRepository: BrandsRepository) {
    suspend operator fun invoke(brandId: String) = brandRepository.getBrandById(brandId= brandId)
}