package com.khun.petgallery.domain.usecase.pets

import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.domain.mappers.PetDataModel
import com.khun.petgallery.domain.mappers.mapFavPetsDataItem
import com.khun.petgallery.domain.repositories.PetRepository
import com.khun.petgallery.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavPetsUseCaseImpl(private val petRepository: PetRepository) : GetFavPetsUseCase {
    override suspend fun execute(): Flow<NetworkResult<List<PetDataModel>>> =
        flow {
            petRepository.fetchFavouritePets(Constants.SUB_ID).collect { favPet ->
                when (favPet) {
                    is NetworkResult.Success -> {
                        val pets = favPet.data?.map { pet ->
                            pet.mapFavPetsDataItem()
                        }
                    }

                    is NetworkResult.Error -> {
                        emit(NetworkResult.Error(favPet.message))
                    }

                    is NetworkResult.Loading -> {
                        emit(NetworkResult.Loading())
                    }
                }
            }
        }
}