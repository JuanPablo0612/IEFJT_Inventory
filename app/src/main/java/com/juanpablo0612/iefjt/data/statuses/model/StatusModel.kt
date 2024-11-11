package com.juanpablo0612.iefjt.data.statuses.model

import com.juanpablo0612.iefjt.domain.model.Status

data class StatusModel(
    val id: String = "",
    val name: String = "",
    val color: String = "#000000"
)

fun StatusModel.toDomain() = Status(
    id = id,
    name = name,
    color = color
)
