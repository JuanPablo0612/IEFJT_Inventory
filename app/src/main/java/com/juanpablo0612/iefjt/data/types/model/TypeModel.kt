package com.juanpablo0612.iefjt.data.types.model

import com.juanpablo0612.iefjt.domain.model.Type

data class TypeModel(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = ""
)

fun TypeModel.toDomain() = Type(
    id = id,
    name = name,
    imageUrl = imageUrl
)
