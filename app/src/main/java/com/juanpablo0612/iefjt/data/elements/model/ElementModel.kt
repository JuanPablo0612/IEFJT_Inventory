package com.juanpablo0612.iefjt.data.elements.model

import com.google.firebase.firestore.ServerTimestamp
import com.juanpablo0612.iefjt.data.brands.model.BrandModel
import com.juanpablo0612.iefjt.data.brands.model.toDomain
import com.juanpablo0612.iefjt.data.statuses.model.StatusModel
import com.juanpablo0612.iefjt.data.statuses.model.toDomain
import com.juanpablo0612.iefjt.data.types.model.TypeModel
import com.juanpablo0612.iefjt.data.types.model.toDomain
import com.juanpablo0612.iefjt.domain.model.Element
import com.juanpablo0612.iefjt.domain.model.ElementStatusHistory
import java.util.Date

data class ElementModel(
    val id: String = "",
    val name: String = "",
    val serial: String = "",
    val location: String = "",
    @ServerTimestamp val lastUpdate: Date? = null,
    val status: StatusModel = StatusModel(),
    val type: TypeModel = TypeModel(),
    val brand: BrandModel = BrandModel(),
    val statusHistory: List<ElementStatusHistoryModel> = emptyList()
)

data class ElementStatusHistoryModel(
    val id: String = "",
    val updatedAt: Date? = null,
    val name: String = "",
    val color: String = ""
)

fun ElementModel.toDomain() = Element(
    id = id,
    name = name,
    serial = serial,
    location = location,
    lastUpdate = lastUpdate!!,
    status = status.toDomain(),
    type = type.toDomain(),
    brand = brand.toDomain(),
    statusHistory = statusHistory.map { it.toDomain() }
)

fun ElementStatusHistoryModel.toDomain() = ElementStatusHistory(
    id = id,
    updatedAt = updatedAt!!,
    name = name,
    color = color
)
