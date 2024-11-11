package com.khun.petgallery.domain.usecase.petsDetails

import com.khun.petgallery.domain.repositories.PetDetailsRepository
import kotlinx.coroutines.flow.Flow

class CheckFavUseCaseImpl(private val petDetailsRepository: PetDetailsRepository) : CheckFavUseCase {
    override suspend fun execute(imageId: String): Flow<Int?> = petDetailsRepository.isFavourite(imageId)
}