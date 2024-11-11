package com.khun.petgallery.domain.usecase.petsDetails

import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.domain.mappers.CallSuccessModel
import com.khun.petgallery.domain.mappers.mapSuccessData
import com.khun.petgallery.domain.repositories.PetDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteFavPetUseCaseImpl(private val petDetailsRepository: PetDetailsRepository) :
    DeleteFavPetUseCase {
    override suspend fun execute(
        imageId: String,
        favId: Int
    ): Flow<NetworkResult<CallSuccessModel>> =
        flow {
            petDetailsRepository.deleteFavouritePet(imageId, favId).collect { response ->
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