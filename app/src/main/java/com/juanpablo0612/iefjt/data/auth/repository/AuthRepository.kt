package com.juanpablo0612.iefjt.data.auth.repository

import com.juanpablo0612.iefjt.data.auth.remote.AuthRemoteDataSource
import com.juanpablo0612.iefjt.data.exceptions.resultOf
import javax.inject.Inject

class AuthRepository @Inject constructor(private val remoteDataSource: AuthRemoteDataSource) {
    suspend fun login(email: String, password: String): Result<String> {
        return resultOf {
            remoteDataSource.login(email, password)
        }
    }

    suspend fun register(email: String, password: String): Result<String> {
        return resultOf {
            remoteDataSource.register(email, password)
        }
    }

    fun logout(): Result<Unit> {
        return resultOf {
            remoteDataSource.logout()
        }
    }

    fun isLoggedIn(): Result<Boolean> {
        return resultOf {
            remoteDataSource.isLoggedIn()
        }
    }

    suspend fun updateAuthInfo(name: String): Result<Unit> {
        return resultOf {
            remoteDataSource.updateAuthInfo(name)
        }
    }

    fun getUid(): Result<String?> {
        return resultOf {
            remoteDataSource.getUid()
        }
    }
}