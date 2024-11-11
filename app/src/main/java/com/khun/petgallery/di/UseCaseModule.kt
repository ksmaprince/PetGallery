package com.khun.petgallery.di

import com.khun.petgallery.domain.repositories.PetDetailsRepository
import com.khun.petgallery.domain.repositories.PetRepository
import com.khun.petgallery.domain.usecase.pets.GetFavPetsUseCase
import com.khun.petgallery.domain.usecase.pets.GetFavPetsUseCaseImpl
import com.khun.petgallery.domain.usecase.pets.GetPetsUseCase
import com.khun.petgallery.domain.usecase.pets.GetPetsUseCaseImpl
import com.khun.petgallery.domain.usecase.petsDetails.CheckFavUseCase
import com.khun.petgallery.domain.usecase.petsDetails.CheckFavUseCaseImpl
import com.khun.petgallery.domain.usecase.petsDetails.DeleteFavPetUseCase
import com.khun.petgallery.domain.usecase.petsDetails.DeleteFavPetUseCaseImpl
import com.khun.petgallery.domain.usecase.petsDetails.PostFavPetUseCase
import com.khun.petgallery.domain.usecase.petsDetails.PostFavPetUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun providePetUseCase(petRepository: PetRepository): GetPetsUseCase =
        GetPetsUseCaseImpl(petRepository)

    @Provides
    fun provideFavPetUseCase(petRepository: PetRepository): GetFavPetsUseCase =
        GetFavPetsUseCaseImpl(petRepository)

    @Provides
    fun providePostFavUseCase(petDetailsRepository: PetDetailsRepository): PostFavPetUseCase =
        PostFavPetUseCaseImpl(petDetailsRepository)

    @Provides
    fun provideDeleteFavUseCase(petDetailsRepository: PetDetailsRepository): DeleteFavPetUseCase =
        DeleteFavPetUseCaseImpl(petDetailsRepository)

    @Provides
    fun provideCheckFavUseCase(petDetailsRepository: PetDetailsRepository): CheckFavUseCase =
        CheckFavUseCaseImpl(petDetailsRepository)
}