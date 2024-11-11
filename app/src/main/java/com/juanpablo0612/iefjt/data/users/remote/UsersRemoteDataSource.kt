package com.juanpablo0612.iefjt.data.users.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.juanpablo0612.iefjt.data.users.model.UserModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(firestore: FirebaseFirestore) {
    private val usersCollection = firestore.collection("users")

    fun getUserByUid(uid: String) =
        usersCollection.document(uid).dataObjects<UserModel>().filterNotNull()

    suspend fun saveUser(user: UserModel) {
        usersCollection.document(user.uid).set(user).await()
    }
}