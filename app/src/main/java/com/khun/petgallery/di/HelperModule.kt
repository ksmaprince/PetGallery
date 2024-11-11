package com.khun.petgallery.di

import com.khun.petgallery.data.database.AppDatabase
import com.khun.petgallery.data.database.pets.PetsDatabaseHelper
import com.khun.petgallery.data.database.pets.PetsDatabaseHelperImpl
import com.khun.petgallery.data.database.petsDetails.PetsDetailsDatabaseHelper
import com.khun.petgallery.data.database.petsDetails.PetsDetailsDatabaseHelperImpl
import com.khun.petgallery.data.remote.PetsService
import com.khun.petgallery.data.remote.pets.PetApiServiceHelper
import com.khun.petgallery.data.remote.pets.PetApiServiceHelperImpl
import com.khun.petgallery.data.remote.petsDetail.PetDetailsApiServiceHelper
import com.khun.petgallery.data.remote.petsDetail.PetDetailsApiServiceHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HelperModule {
    @Provides
    fun providePetsDatabaseHelper(appDatabase: AppDatabase): PetsDatabaseHelper =
        PetsDatabaseHelperImpl(appDatabase)

    @Provides
    fun providePetsDetailsDatabaseHelper(appDatabase: AppDatabase): PetsDetailsDatabaseHelper =
        PetsDetailsDatabaseHelperImpl(appDatabase)

    @Provides
    fun providePetApiServiceHelper(petsService: PetsService): PetApiServiceHelper =
        PetApiServiceHelperImpl(petsService)

    @Provides
    fun providePetDetailApiServiceHelper(petsService: PetsService): PetDetailsApiServiceHelper =
        PetDetailsApiServiceHelperImpl(petsService)
}