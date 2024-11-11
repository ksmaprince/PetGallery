package com.khun.petgallery.data.repositories

import com.khun.petgallery.data.database.petsDetails.PetsDetailsDatabaseHelper
import com.khun.petgallery.data.models.SuccessResponse
import com.khun.petgallery.data.models.petDetails.PostFavPetModel
import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.data.remote.petsDetail.PetDetailsApiServiceHelper
import com.khun.petgallery.domain.repositories.PetDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PetsDetailsRepositoryImpl(
    private val petDetailsApiServiceHelper: PetDetailsApiServiceHelper,
    private val petsDetailsDatabaseHelper: PetsDetailsDatabaseHelper
) : PetDetailsRepository {
    override suspend fun postFavouritePet(favPet: PostFavPetModel): Flow<NetworkResult<SuccessResponse>> =
        flow<NetworkResult<SuccessResponse>> {
            emit(NetworkResult.Loading())
            with(petDetailsApiServiceHelper.postFavouritePet(favPet)) {
                if (isSuccessful) {
                    emit(NetworkResult.Success(this.body()))
                    this.body()?.id?.let {
                        petsDetailsDatabaseHelper.insertFavPetImageRelation(it, favPet.imageId)
                    }
                } else emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }


    override suspend fun deleteFavouritePet(
        imageId: String,
        favouriteId: Int
    ): Flow<NetworkResult<SuccessResponse>> =
        flow<NetworkResult<SuccessResponse>> {
            emit(NetworkResult.Loading())
            with(petDetailsApiServiceHelper.deleteFavouritePet(favouriteId)) {
                if (isSuccessful) {
                    emit(NetworkResult.Success(this.body()))
                    petsDetailsDatabaseHelper.deleteFavImage(imageId)
                } else emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }

    override suspend fun isFavourite(imageId: String): Flow<Int?> =
        flow {
            emit(petsDetailsDatabaseHelper.isFavourite(imageId))
        }
}