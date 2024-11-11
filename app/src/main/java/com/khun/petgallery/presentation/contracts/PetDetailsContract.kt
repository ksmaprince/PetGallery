package com.khun.petgallery.presentation.contracts

import com.khun.petgallery.domain.mappers.CallSuccessModel

class PetDetailsContract {
    data class State(
        val postFavPetSuccess: CallSuccessModel?,
        val deleteFavPetSuccess: CallSuccessModel?
    )
}