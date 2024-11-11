package com.juanpablo0612.iefjt.data.exceptions

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

fun Exception.toAppException() = when (this) {
    is AppException -> this
    is FirebaseAuthUserCollisionException -> UserAlreadyExistsException()
    is FirebaseAuthInvalidCredentialsException -> InvalidCredentialsException()
    is FirebaseNetworkException -> NetworkException()
    else -> UnknownException()
}

inline fun <T> resultOf(action: () -> T): Result<T> {
    return try {
        Result.success(action.invoke())
    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(e.toAppException())
    }
}

open class AppException : Exception()

class UserAlreadyExistsException : AppException()
class InvalidCredentialsException : AppException()
class UserIsNotSignedInException : AppException()
class NetworkException : AppException()
class UnknownException : AppException()