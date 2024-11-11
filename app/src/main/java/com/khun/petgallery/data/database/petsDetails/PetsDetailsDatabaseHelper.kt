package com.khun.petgallery.data.database.petsDetails

interface PetsDetailsDatabaseHelper {
    suspend fun insertFavPetImageRelation(favCatId: Int, imageId: String): Long
    suspend fun deleteFavImage(petImageId: String): Int
    suspend fun isFavourite(petImageId: String): Int?
}