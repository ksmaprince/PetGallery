package com.khun.petgallery.domain.usecase.pets

import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.domain.mappers.PetDataModel
import com.khun.petgallery.domain.mappers.mapPetsDataItems
import com.khun.petgallery.domain.repositories.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPetsUseCaseImpl(private val petRepository: PetRepository) : GetPetsUseCase {
    override suspend fun execute(): Flow<NetworkResult<List<PetDataModel>>> =
        flow {
            petRepository.fetchPets().collect { petResponse ->
                when (petResponse) {
                    is NetworkResult.Success -> {
                        val pets = petResponse.data?.map { pet ->
                            pet.mapPetsDataItems()
                        }
                        emit(NetworkResult.Success(pets))
                    }

                    is NetworkResult.Error -> {
                        emit(NetworkResult.Error(petResponse.message))
                    }

                    is NetworkResult.Loading -> {
                        emit(NetworkResult.Loading())
                    }
                }
            }
        }
}