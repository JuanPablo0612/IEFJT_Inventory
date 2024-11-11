package com.juanpablo0612.iefjt.data.users.model

import com.google.firebase.firestore.ServerTimestamp
import com.juanpablo0612.iefjt.domain.model.User
import java.util.Date

data class UserModel(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "",
    @ServerTimestamp val createdAt: Date? = null
)

fun UserModel.toDomain() = User(
    uid = uid,
    name = name,
    email = email,
    role = role,
    createdAt = createdAt
)
