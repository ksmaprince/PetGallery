package com.khun.petgallery.domain.usecase.pets

import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.domain.mappers.PetDataModel
import kotlinx.coroutines.flow.Flow

interface GetFavPetsUseCase {
    suspend fun execute(): Flow<NetworkResult<List<PetDataModel>>>
}