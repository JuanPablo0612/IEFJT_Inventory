package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.auth.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val saveUserUseCase: SaveUserUseCase
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Result<String> {
        val registrationResult = authRepository.register(email, password)
        if (registrationResult.isFailure) {
            return Result.failure(registrationResult.exceptionOrNull()!!)
        }

        val uid = registrationResult.getOrNull()!!

        val saveUserResult = saveUserUseCase(id = uid, name = name, email = email)
        if (saveUserResult.isFailure) {
            authRepository.logout()
            return Result.failure(saveUserResult.exceptionOrNull()!!)
        }

        val updateAuthResult = authRepository.updateAuthInfo(name)
        if (updateAuthResult.isFailure) {
            return Result.failure(updateAuthResult.exceptionOrNull()!!)
        }

        return Result.success(uid)
    }
}
