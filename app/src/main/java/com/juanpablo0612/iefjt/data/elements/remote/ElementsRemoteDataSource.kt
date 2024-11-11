package com.juanpablo0612.iefjt.data.elements.remote

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObjects
import com.juanpablo0612.iefjt.data.elements.model.ElementModel
import com.juanpablo0612.iefjt.data.elements.model.ElementStatusHistoryModel
import com.juanpablo0612.iefjt.data.statuses.model.StatusModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

class ElementsRemoteDataSource @Inject constructor(firestore: FirebaseFirestore) {
    private val elementsCollection = firestore.collection("elements")

    suspend fun getElements(): List<ElementModel> {
        val elementsDocuments = elementsCollection.get().await()
        return elementsDocuments.toObjects<ElementModel>()
    }

    fun getElementById(elementId: String): Flow<ElementModel?> {
        val elementDocument = elementsCollection.document(elementId)
        return elementDocument.dataObjects<ElementModel>()
    }

    fun getLastMonthUpdatedElements(): Flow<List<ElementModel>> {
        val now = Calendar.getInstance()
        val startOfMonth = now.apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val elementsDocuments = elementsCollection.whereGreaterThan("lastUpdate", startOfMonth)

        return elementsDocuments.dataObjects<ElementModel>()
    }

    fun getElementsByStatus(statusId: String): Flow<List<ElementModel>> {
        val elementsDocuments = elementsCollection.whereEqualTo("status.id", statusId)

        return elementsDocuments.dataObjects<ElementModel>()
    }

    fun getElementsByType(typeId: String): Flow<List<ElementModel>> {
        val elementsDocuments = elementsCollection.whereEqualTo("type.id", typeId)

        return elementsDocuments.dataObjects<ElementModel>()
    }

    fun getElementsByBrand(brandId: String): Flow<List<ElementModel>> {
        val elementsDocuments = elementsCollection.whereEqualTo("brand.id", brandId)

        return elementsDocuments.dataObjects<ElementModel>()
    }

    fun getElementBySerial(serial: String): Flow<ElementModel?> {
        val elementsDocuments = elementsCollection.whereEqualTo("serial", serial)

        return elementsDocuments.dataObjects<ElementModel>().map { it[0] }
    }

    suspend fun saveElement(element: ElementModel) {
        val statusHistory = ElementStatusHistoryModel(
            id = element.status.id,
            updatedAt = Calendar.getInstance().time,
            name = element.status.name,
            color = element.status.color
        )

        val elementId = elementsCollection.document().id
        val newElement = element.copy(id = elementId, statusHistory = listOf(statusHistory))
        elementsCollection.document(elementId).set(newElement).await()
    }

    suspend fun updateElementStatus(elementId: String, status: StatusModel) {
        val statusHistory = ElementStatusHistoryModel(
            id = status.id,
            updatedAt = Calendar.getInstance().time,
            name = status.name,
            color = status.color
        )

        elementsCollection.document(elementId)
            .update(
                "lastUpdate",
                Calendar.getInstance().time,
                "status",
                status,
                "statusHistory",
                FieldValue.arrayUnion(statusHistory)
            ).await()
    }
}