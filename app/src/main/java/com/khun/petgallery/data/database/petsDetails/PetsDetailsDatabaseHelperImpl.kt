package com.khun.petgallery.data.database.petsDetails

import com.khun.petgallery.data.database.AppDatabase
import com.khun.petgallery.data.database.entity.FavImageEntity

class PetsDetailsDatabaseHelperImpl(private val db: AppDatabase) :
    PetsDetailsDatabaseHelper {
    override suspend fun insertFavPetImageRelation(favPatId: Int, imageId: String): Long {
        return FavImageEntity(favPatId, imageId).let {
            db.favImageDao().insertFavPetImageRelation(it)
        }
    }

    override suspend fun deleteFavImage(petImageId: String): Int {
        return db.favImageDao().deleteFavImage(petImageId)
    }

    override suspend fun isFavourite(petImageId: String): Int? {
        return db.favImageDao().getFavId(petImageId)
    }
}