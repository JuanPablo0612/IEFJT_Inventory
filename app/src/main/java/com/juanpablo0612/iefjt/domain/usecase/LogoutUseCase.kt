package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.auth.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): Result<Unit> {
        return authRepository.logout()
    }
}