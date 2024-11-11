package com.juanpablo0612.iefjt.domain.usecase

import com.juanpablo0612.iefjt.data.users.repository.UsersRepository
import javax.inject.Inject

class GetUserByUidUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    operator fun invoke(uid: String) = usersRepository.getUserByUid(uid)
}