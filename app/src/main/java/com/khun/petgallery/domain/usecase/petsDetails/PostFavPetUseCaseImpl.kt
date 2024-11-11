package com.khun.petgallery.domain.usecase.petsDetails

import com.khun.petgallery.data.models.petDetails.PostFavPetModel
import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.domain.mappers.CallSuccessModel
import com.khun.petgallery.domain.mappers.mapSuccessData
import com.khun.petgallery.domain.repositories.PetDetailsRepository
import com.khun.petgallery.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostFavPetUseCaseImpl(private val petDetailsRepository: PetDetailsRepository) :
    PostFavPetUseCase {
    override suspend fun execute(imageId: String): Flow<NetworkResult<CallSuccessModel>> =
        flow {
            val favPet = PostFavPetModel(imageId, Constants.SUB_ID)
            petDetailsRepository.postFavouritePet(favPet).collect { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        emit(NetworkResult.Success(response.data?.mapSuccessData()))
                    }

                    is NetworkResult.Error -> {
                        emit(NetworkResult.Error(response.message))
                    }

                    is NetworkResult.Loading -> {
                        emit(NetworkResult.Loading())
                    }
                }

            }
        }
}