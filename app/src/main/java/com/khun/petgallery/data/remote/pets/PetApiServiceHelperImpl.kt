package com.khun.petgallery.data.remote.pets

import com.khun.petgallery.data.models.petData.FavouritePetsItem
import com.khun.petgallery.data.models.petData.PetResponse
import com.khun.petgallery.data.remote.PetsService
import retrofit2.Response

class PetApiServiceHelperImpl (private val petsService: PetsService) :
    PetApiServiceHelper {
    override suspend fun fetchPetsImages(limit: Int): Response<List<PetResponse>> =
        petsService.fetchPetsImages(limit)

    override suspend fun fetchFavouritePets(subId: String): Response<List<FavouritePetsItem>> =
        petsService.fetchFavouritePets(subId)

}