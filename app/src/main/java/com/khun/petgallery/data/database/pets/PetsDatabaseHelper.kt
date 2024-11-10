package com.khun.petgallery.data.database.pets

import com.khun.petgallery.data.models.petData.FavouritePetsItem

interface PetsDatabaseHelper {
    suspend fun insertFavCatImageRelation(favCatItems: List<FavouritePetsItem>): List<Long>
}