package com.juanpablo0612.iefjt.data.types.repository

import android.net.Uri
import com.juanpablo0612.iefjt.data.exceptions.resultOf
import com.juanpablo0612.iefjt.data.types.model.toDomain
import com.juanpablo0612.iefjt.data.types.remote.TypesRemoteDataSource
import com.juanpablo0612.iefjt.domain.model.Type
import com.juanpablo0612.iefjt.domain.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TypesRepository @Inject constructor(private val remoteDataSource: TypesRemoteDataSource) {
    fun getTypes(): Flow<List<Type>> {
        return remoteDataSource.getTypes().map { it.map { model -> model.toDomain() } }
    }

    suspend fun getTypeById(typeId: String): Result<Type> {
        return resultOf {
            remoteDataSource.getTypeById(typeId).toDomain()
        }
    }

    suspend fun saveType(type: Type, imageUri: Uri? = null): Result<Unit> {
        return resultOf {
            val model = type.toModel()
            remoteDataSource.saveType(type = model, imageUri = imageUri)
        }
    }
}