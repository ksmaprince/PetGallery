package com.khun.petgallery.data.remote.petsDetail

import com.khun.petgallery.data.models.SuccessResponse
import com.khun.petgallery.data.models.petDetails.PostFavPetModel
import com.khun.petgallery.data.remote.PetsService
import retrofit2.Response

class PetDetailsApiServiceHelperImpl(private val petsService: PetsService) :
    PetDetailsApiServiceHelper {
    override suspend fun postFavouritePet(favPet: PostFavPetModel): Response<SuccessResponse> =
        petsService.postFavouritePet(favPet)


    override suspend fun deleteFavouritePet(favouriteId: Int): Response<SuccessResponse> =
        petsService.deleteFavouritePet(favouriteId)

}