package com.juanpablo0612.iefjt.domain.model

import com.juanpablo0612.iefjt.data.statuses.model.StatusModel

data class Status(
    val id: String = "",
    val name: String = "",
    val color: String = "#000000"
)

fun Status.toModel() = StatusModel(
    id = id,
    name = name,
    color = color
)
