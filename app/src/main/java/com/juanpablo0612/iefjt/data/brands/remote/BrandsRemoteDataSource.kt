package com.juanpablo0612.iefjt.data.brands.remote

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.juanpablo0612.iefjt.data.brands.model.BrandModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BrandsRemoteDataSource @Inject constructor(
    firestore: FirebaseFirestore,
    storage: FirebaseStorage
) {
    private val brandsCollection = firestore.collection("brands")
    private val brandsStorage = storage.reference.child("brands")

    suspend fun getBrandById(brandId: String): BrandModel {
        val brandDocument = brandsCollection.document(brandId).get().await()
        return brandDocument.toObject<BrandModel>()!!
    }

    fun getBrands() = brandsCollection.dataObjects<BrandModel>()

    suspend fun saveBrand(brand: BrandModel, imageUri: Uri? = null) {
        val brandId = brandsCollection.document().id
        val imageUrl = if (imageUri != null) {
            uploadBrandImage(brandId, imageUri)
        } else brand.imageUrl

        val newBrand = brand.copy(id = brandId, imageUrl = imageUrl)
        brandsCollection.document(brandId).set(newBrand).await()
    }

    private suspend fun uploadBrandImage(brandId: String, imageUri: Uri): String {
        val imageRef = brandsStorage.child("$brandId/image")
        val uploadTask = imageRef.putFile(imageUri)
        return uploadTask.await().storage.downloadUrl.await().toString()
    }
}