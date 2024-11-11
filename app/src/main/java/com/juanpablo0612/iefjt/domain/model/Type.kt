package com.juanpablo0612.iefjt.domain.model

import com.juanpablo0612.iefjt.data.types.model.TypeModel

data class Type(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = ""
)

fun Type.toModel() = TypeModel(
    id = id,
    name = name,
    imageUrl = imageUrl
)
