package com.juanpablo0612.iefjt.domain.model

import com.juanpablo0612.iefjt.data.users.model.UserModel
import java.util.Date

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "user",
    val createdAt: Date? = null
)

fun User.toModel() = UserModel(
    uid = uid,
    name = name,
    email = email,
    role = role,
    createdAt = createdAt
)
