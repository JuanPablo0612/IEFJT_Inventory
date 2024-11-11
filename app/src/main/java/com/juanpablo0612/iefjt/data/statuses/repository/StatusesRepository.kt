package com.juanpablo0612.iefjt.data.statuses.repository

import com.juanpablo0612.iefjt.data.exceptions.resultOf
import com.juanpablo0612.iefjt.data.statuses.model.toDomain
import com.juanpablo0612.iefjt.data.statuses.remote.StatusesRemoteDataSource
import com.juanpablo0612.iefjt.domain.model.Status
import com.juanpablo0612.iefjt.domain.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StatusesRepository @Inject constructor(private val remoteDataSource: StatusesRemoteDataSource) {
    suspend fun getStatusById(statusId: String): Result<Status> {
        return resultOf {
            remoteDataSource.getStatusById(statusId = statusId).toDomain()
        }
    }

    fun getStatuses(): Flow<List<Status>> {
        return remoteDataSource.getStatuses().map { it.map { model -> model.toDomain() } }
    }

    suspend fun saveStatus(status: Status): Result<Unit> {
        return resultOf {
            val model = status.toModel()
            remoteDataSource.saveStatus(model)
        }
    }
}