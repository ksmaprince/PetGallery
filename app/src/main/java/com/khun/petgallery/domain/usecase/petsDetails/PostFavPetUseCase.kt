package com.khun.petgallery.domain.usecase.petsDetails

import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.domain.mappers.CallSuccessModel
import kotlinx.coroutines.flow.Flow

interface PostFavPetUseCase {
    suspend fun execute(imageId: String): Flow<NetworkResult<CallSuccessModel>>
}