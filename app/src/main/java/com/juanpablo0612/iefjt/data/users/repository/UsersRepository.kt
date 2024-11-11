package com.juanpablo0612.iefjt.data.users.repository

import com.juanpablo0612.iefjt.data.exceptions.resultOf
import com.juanpablo0612.iefjt.data.exceptions.toAppException
import com.juanpablo0612.iefjt.data.users.model.toDomain
import com.juanpablo0612.iefjt.data.users.remote.UsersRemoteDataSource
import com.juanpablo0612.iefjt.domain.model.User
import com.juanpablo0612.iefjt.domain.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepository @Inject constructor(private val  remoteDataSource: UsersRemoteDataSource) {
    fun getUserByUid(uid: String): Flow<User> {
        return remoteDataSource.getUserByUid(uid)
            .catch { throw (it as Exception).toAppException() }
            .map { it.toDomain() }
    }

    suspend fun saveUser(user: User): Result<Unit> {
        return resultOf {
            val model = user.toModel()
            remoteDataSource.saveUser(model)
        }
    }
}