package com.khun.petgallery.di

import com.khun.petgallery.data.database.pets.PetsDatabaseHelper
import com.khun.petgallery.data.database.petsDetails.PetsDetailsDatabaseHelper
import com.khun.petgallery.data.remote.pets.PetApiServiceHelper
import com.khun.petgallery.data.remote.petsDetail.PetDetailsApiServiceHelper
import com.khun.petgallery.data.repositories.PetsDetailsRepositoryImpl
import com.khun.petgallery.data.repositories.PetsRepositoryImpl
import com.khun.petgallery.domain.repositories.PetDetailsRepository
import com.khun.petgallery.domain.repositories.PetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun providePetRepository(
        petsApiServiceHelper: PetApiServiceHelper,
        petsDatabaseHelper: PetsDatabaseHelper
    ): PetRepository {
        return PetsRepositoryImpl(petsApiServiceHelper, petsDatabaseHelper)
    }

    @Provides
    fun providePetsDetailsRepository(
        petDetailsApiServiceHelper: PetDetailsApiServiceHelper,
        petsDetailsDatabaseHelper: PetsDetailsDatabaseHelper
    ): PetDetailsRepository {
        return PetsDetailsRepositoryImpl(petDetailsApiServiceHelper, petsDetailsDatabaseHelper)
    }
}