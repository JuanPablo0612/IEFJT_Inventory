package com.juanpablo0612.iefjt.domain.model

import com.juanpablo0612.iefjt.data.brands.model.BrandModel

data class Brand(
    val id: String= "",
    val name: String = "",
    val imageUrl: String = ""
)

fun Brand.toModel() = BrandModel(
    id = id,
    name = name,
    imageUrl = imageUrl
)
