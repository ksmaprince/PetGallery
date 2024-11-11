package com.khun.petgallery.domain.usecase.petsDetails

import kotlinx.coroutines.flow.Flow

interface CheckFavUseCase {
    suspend fun execute(imageId: String): Flow<Int?>
}