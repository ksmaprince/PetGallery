package com.khun.petgallery.data.remote.pets

import com.khun.petgallery.data.models.petData.FavouritePetsItem
import com.khun.petgallery.data.models.petData.PetResponse
import retrofit2.Response

interface PetApiServiceHelper {
    suspend fun fetchPetsImages(limit: Int): Response<List<PetResponse>>
    suspend fun fetchFavouritePets(subId: String): Response<List<FavouritePetsItem>>

}