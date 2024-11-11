package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.auth.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return authRepository.login(email, password)
    }
}