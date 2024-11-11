package com.khun.petgallery.domain.repositories

import com.khun.petgallery.data.models.petData.FavouritePetsItem
import com.khun.petgallery.data.models.petData.PetResponse
import com.khun.petgallery.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    suspend fun fetchPets(limit: Int = 10): Flow<NetworkResult<List<PetResponse>>>
    suspend fun fetchFavouritePets(subId: String): Flow<NetworkResult<List<FavouritePetsItem>>>
    suspend fun fetchTestFavouritePets(subId: String): Flow<NetworkResult<List<FavouritePetsItem>>>

}