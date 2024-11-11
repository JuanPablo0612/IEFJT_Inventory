package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.users.repository.UsersRepository
import com.juanpablo0612.iefjt.domain.model.User
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(
        id: String,
        name: String,
        email: String
    ): Result<Unit> {
        val user = User(uid = id, name = name, email = email)
        return usersRepository.saveUser(user)
    }
}