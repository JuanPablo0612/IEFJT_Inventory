package com.juanpablo0612.iefjt.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
object Home

@Serializable
object AddElement

@Serializable
object AddBrand

@Serializable
object AddType

@Serializable
object AddStatus

@Serializable
data class ElementDetail(val id: String)

@Serializable
data class UpdateElementStatus(val elementId: String, val currentStatusId: String)

@Serializable
data class ElementList(
    val brandId: String? = null,
    val typeId: String? = null,
    val statusId: String? = null
)