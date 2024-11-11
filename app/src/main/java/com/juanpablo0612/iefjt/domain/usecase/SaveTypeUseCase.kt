package com.juanpablo0612.iefjt.domain.usecase

import android.net.Uri
import com.juanpablo0612.iefjt.data.types.repository.TypesRepository
import com.juanpablo0612.iefjt.domain.model.Type
import javax.inject.Inject

class SaveTypeUseCase @Inject constructor(private val typesRepository: TypesRepository) {
    suspend operator fun invoke(name: String, imageUrl: String? = null, imageUri: Uri? = null): Result<Unit> {
        val type = Type(
            name = name,
            imageUrl = imageUrl ?: ""
        )

        return typesRepository.saveType(type, imageUri)
    }
}