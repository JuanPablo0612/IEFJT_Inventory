package com.juanpablo0612.iefjt.data.statuses.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.juanpablo0612.iefjt.data.statuses.model.StatusModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StatusesRemoteDataSource @Inject constructor(firestore: FirebaseFirestore) {
    private val statusesCollection = firestore.collection("statuses")

    suspend fun getStatusById(statusId: String): StatusModel {
        val statusDocument = statusesCollection.document(statusId).get().await()
        return statusDocument.toObject<StatusModel>()!!
    }

    fun getStatuses() = statusesCollection.dataObjects<StatusModel>()

    suspend fun saveStatus(status: StatusModel) {
        val statusId = statusesCollection.document().id
        val newStatus = status.copy(id = statusId)
        statusesCollection.document(statusId).set(newStatus).await()
    }
}