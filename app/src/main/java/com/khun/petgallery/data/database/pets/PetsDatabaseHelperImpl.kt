package com.khun.petgallery.data.database.pets

import com.khun.petgallery.data.database.AppDatabase
import com.khun.petgallery.data.database.entity.FavImageEntity
import com.khun.petgallery.data.models.petData.FavouritePetsItem

class PetsDatabaseHelperImpl(private val db: AppDatabase) : PetsDatabaseHelper {
    override suspend fun insertFavCatImageRelation(favCatItems: List<FavouritePetsItem>): List<Long> {
        val favPetRelList = favCatItems.map {
            FavImageEntity(
                favouriteId = it.id,
                imageId = it.imageId
            )
        }
        return favPetRelList.let { db.favImageDao().insertFavCatImageRelation(favPetRelList) }
    }
}