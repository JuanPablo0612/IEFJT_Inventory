package com.juanpablo0612.iefjt.data.types.remote

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.juanpablo0612.iefjt.data.types.model.TypeModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TypesRemoteDataSource @Inject constructor(
    firestore: FirebaseFirestore,
    storage: FirebaseStorage
) {
    private val typesCollection = firestore.collection("types")
    private val typesStorage = storage.reference.child("types")

    fun getTypes() = typesCollection.dataObjects<TypeModel>()

    suspend fun getTypeById(typeId: String): TypeModel {
        val typeDocument = typesCollection.document(typeId).get().await()
        return typeDocument.toObject<TypeModel>()!!
    }

    suspend fun saveType(type: TypeModel, imageUri: Uri? = null) {
        val typeId = typesCollection.document().id
        val imageUrl = if (imageUri != null) {
            uploadTypeImage(typeId, imageUri)
        } else type.imageUrl

        val newType = type.copy(id = typeId, imageUrl = imageUrl)
        typesCollection.document(typeId).set(newType).await()
    }

    private suspend fun uploadTypeImage(typeId: String, imageUri: Uri): String {
        val imageRef = typesStorage.child("$typeId/image")
        val uploadTask = imageRef.putFile(imageUri)
        return uploadTask.await().storage.downloadUrl.await().toString()
    }
}