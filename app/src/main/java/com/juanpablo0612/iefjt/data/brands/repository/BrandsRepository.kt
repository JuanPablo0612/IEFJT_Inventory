package com.juanpablo0612.iefjt.data.brands.repository

import android.net.Uri
import com.juanpablo0612.iefjt.data.brands.model.toDomain
import com.juanpablo0612.iefjt.data.brands.remote.BrandsRemoteDataSource
import com.juanpablo0612.iefjt.data.exceptions.resultOf
import com.juanpablo0612.iefjt.domain.model.Brand
import com.juanpablo0612.iefjt.domain.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BrandsRepository @Inject constructor(private val remoteDataSource: BrandsRemoteDataSource) {
    fun getBrands(): Flow<List<Brand>> {
        return remoteDataSource.getBrands().map { it.map { model -> model.toDomain() } }
    }

    suspend fun getBrandById(brandId: String): Result<Brand> {
        return resultOf {
            remoteDataSource.getBrandById(brandId).toDomain()
        }
    }

    suspend fun saveBrand(brand: Brand, imageUri: Uri? = null): Result<Unit> {
        return resultOf {
            val model = brand.toModel()
            remoteDataSource.saveBrand(model, imageUri)
        }
    }
}