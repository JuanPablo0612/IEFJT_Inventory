package com.juanpablo0612.iefjt.domain.model

import com.juanpablo0612.iefjt.data.elements.model.ElementModel
import com.juanpablo0612.iefjt.data.elements.model.ElementStatusHistoryModel
import java.util.Date

data class Element(
    val id: String = "",
    val name: String = "",
    val serial: String = "",
    val location: String = "",
    val lastUpdate: Date = Date(),
    val status: Status = Status(),
    val type: Type = Type(),
    val brand: Brand = Brand(),
    val statusHistory: List<ElementStatusHistory> = emptyList(),
)

data class ElementStatusHistory(
    val id: String = "",
    val updatedAt: Date = Date(),
    val name: String = "",
    val color: String = "",
)

fun Element.toModel() = ElementModel(
    id = id,
    name = name,
    serial = serial,
    location = location,
    lastUpdate = lastUpdate,
    status = status.toModel(),
    type = type.toModel(),
    brand = brand.toModel(),
    statusHistory = statusHistory.map { it.toModel() }
)

fun ElementStatusHistory.toModel() = ElementStatusHistoryModel(
    id = id,
    name = name,
    color = color
)
