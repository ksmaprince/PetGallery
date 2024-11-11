package com.khun.petgallery.presentation.contracts

import com.khun.petgallery.domain.mappers.PetDataModel

class PetContract {
    data class State(
        val pets: List<PetDataModel> = listOf(),
        val favPets: List<PetDataModel> = listOf(),
        val isLoading: Boolean = false
    )
}