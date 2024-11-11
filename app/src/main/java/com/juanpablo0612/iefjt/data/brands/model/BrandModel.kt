package com.juanpablo0612.iefjt.data.brands.model

import com.juanpablo0612.iefjt.domain.model.Brand

data class BrandModel(
    val id: String= "",
    val name: String = "",
    val imageUrl: String = ""
)

fun BrandModel.toDomain() = Brand(
    id = id,
    name = name,
    imageUrl = imageUrl
)
