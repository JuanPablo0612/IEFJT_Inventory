package com.juanpablo0612.iefjt.domain.usecase

import android.net.Uri
import com.juanpablo0612.iefjt.data.brands.repository.BrandsRepository
import com.juanpablo0612.iefjt.domain.model.Brand
import javax.inject.Inject

class SaveBrandUseCase @Inject constructor(private val brandsRepository: BrandsRepository) {
    suspend operator fun invoke(name: String, imageUrl: String = "", imageUri: Uri? = null): Result<Unit> {
        val brand = Brand(
            name = name,
            imageUrl = imageUrl
        )

        return brandsRepository.saveBrand(brand, imageUri)
    }
}