package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.elements.repository.ElementsRepository
import com.juanpablo0612.iefjt.domain.model.Brand
import com.juanpablo0612.iefjt.domain.model.Element
import com.juanpablo0612.iefjt.domain.model.Status
import com.juanpablo0612.iefjt.domain.model.Type
import javax.inject.Inject

class SaveElementUseCase @Inject constructor(private val elementsRepository: ElementsRepository) {
    suspend operator fun invoke(name: String, serial: String, location: String, status: Status, brand: Brand, type: Type): Result<Unit> {
        val element = Element(
            name = name,
            serial = serial,
            location = location,
            status = status,
            brand = brand,
            type = type
        )

        return elementsRepository.saveElement(element)
    }
}