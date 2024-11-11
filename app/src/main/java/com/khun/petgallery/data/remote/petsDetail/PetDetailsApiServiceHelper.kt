package com.khun.petgallery.data.remote.petsDetail

import com.khun.petgallery.data.models.SuccessResponse
import com.khun.petgallery.data.models.petDetails.PostFavPetModel
import retrofit2.Response

interface PetDetailsApiServiceHelper {
    suspend fun postFavouritePet(favPet: PostFavPetModel): Response<SuccessResponse>
    suspend fun deleteFavouritePet(favouriteId: Int): Response<SuccessResponse>

}