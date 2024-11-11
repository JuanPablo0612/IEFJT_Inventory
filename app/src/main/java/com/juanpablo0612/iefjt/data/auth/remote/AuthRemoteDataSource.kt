package com.juanpablo0612.iefjt.data.auth.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.juanpablo0612.iefjt.data.exceptions.UserIsNotSignedInException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(private val auth: FirebaseAuth) {
    suspend fun login(email: String, password: String): String {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user!!.uid
    }

    suspend fun register(email: String, password: String): String {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user!!.uid
    }

    fun logout() {
        auth.signOut()
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    suspend fun updateAuthInfo(name: String) {
        val user = auth.currentUser
        user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
            ?.await()
    }

    suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    fun getUid(): String {
        return auth.currentUser?.uid ?: throw UserIsNotSignedInException()
    }
}