package com.juanpablo0612.iefjt.data.elements.repository

import com.juanpablo0612.iefjt.data.elements.model.toDomain
import com.juanpablo0612.iefjt.data.elements.remote.ElementsRemoteDataSource
import com.juanpablo0612.iefjt.data.exceptions.resultOf
import com.juanpablo0612.iefjt.domain.model.Element
import com.juanpablo0612.iefjt.domain.model.Status
import com.juanpablo0612.iefjt.domain.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ElementsRepository @Inject constructor(private val remoteDataSource: ElementsRemoteDataSource) {
    suspend fun getElements(): Result<List<Element>> {
        return resultOf {
            remoteDataSource.getElements().map { it.toDomain() }
        }
    }

    fun getElementById(elementId: String): Flow<Element> {
        return remoteDataSource.getElementById(elementId).map { model -> model!!.toDomain() }
    }

    fun getLastMonthUpdatedElements(): Flow<List<Element>> {
        return remoteDataSource.getLastMonthUpdatedElements()
            .map { it.map { model -> model.toDomain() } }
    }

    fun getElementsByStatus(statusId: String): Flow<List<Element>> {
        return remoteDataSource.getElementsByStatus(statusId)
            .map { it.map { model -> model.toDomain() } }
    }

    fun getElementsByType(typeId: String): Flow<List<Element>> {
        return remoteDataSource.getElementsByType(typeId)
            .map { it.map { model -> model.toDomain() } }
    }

    fun getElementsByBrand(brandId: String): Flow<List<Element>> {
        return remoteDataSource.getElementsByBrand(brandId)
            .map { it.map { model -> model.toDomain() } }
    }

    suspend fun saveElement(element: Element): Result<Unit> {
        return resultOf {
            val model = element.toModel()
            remoteDataSource.saveElement(model)
        }
    }

    suspend fun updateElementStatus(elementId: String, status: Status): Result<Unit> {
        return resultOf {
            val statusModel = status.toModel()
            remoteDataSource.updateElementStatus(elementId, statusModel)
        }
    }
}