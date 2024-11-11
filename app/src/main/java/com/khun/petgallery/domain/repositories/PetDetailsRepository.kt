package com.khun.petgallery.domain.repositories

import com.khun.petgallery.data.models.SuccessResponse
import com.khun.petgallery.data.models.petDetails.PostFavPetModel
import com.khun.petgallery.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow

interface PetDetailsRepository {
    suspend fun postFavouritePet(favCPet: PostFavPetModel): Flow<NetworkResult<SuccessResponse>>
    suspend fun deleteFavouritePet(
        imageId: String,
        favouriteId: Int
    ): Flow<NetworkResult<SuccessResponse>>

    suspend fun isFavourite(imageId: String): Flow<Int?>
}