package com.khun.petgallery.data.repositories

import com.khun.petgallery.data.database.pets.PetsDatabaseHelper
import com.khun.petgallery.data.models.petData.FavouritePetsItem
import com.khun.petgallery.data.models.petData.PetResponse
import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.data.remote.pets.PetApiServiceHelper
import com.khun.petgallery.domain.repositories.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PetsRepositoryImpl(
    private val petsServiceHelper: PetApiServiceHelper,
    private val petsDatabaseHelper: PetsDatabaseHelper
) : PetRepository {
    override suspend fun fetchPets(limit: Int): Flow<NetworkResult<List<PetResponse>>> =
        flow<NetworkResult<List<PetResponse>>> {
            emit(NetworkResult.Loading())
            with(petsServiceHelper.fetchPetsImages(limit)) {
                if (isSuccessful) emit(NetworkResult.Success(this.body()))
                else emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }

    override suspend fun fetchFavouritePets(subId: String): Flow<NetworkResult<List<FavouritePetsItem>>> =
        flow<NetworkResult<List<FavouritePetsItem>>> {
            emit(NetworkResult.Loading())
            with(petsServiceHelper.fetchFavouritePets(subId)) {
                if (isSuccessful) {
                    emit(NetworkResult.Success(this.body()))
                    this.body()?.let {
                        petsDatabaseHelper.insertFavPetImageRelation(it)
                    }
                } else emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }


    override suspend fun fetchTestFavouritePets(subId: String): Flow<NetworkResult<List<FavouritePetsItem>>> =
        flow<NetworkResult<List<FavouritePetsItem>>> {
            emit(NetworkResult.Loading())
            with(petsServiceHelper.fetchFavouritePets(subId)) {
                if (isSuccessful) emit(NetworkResult.Success(this.body()))
                else emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }
}