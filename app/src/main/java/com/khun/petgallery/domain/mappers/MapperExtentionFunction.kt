package com.khun.petgallery.domain.mappers

import com.khun.petgallery.data.models.SuccessResponse
import com.khun.petgallery.data.models.petData.FavouritePetsItem
import com.khun.petgallery.data.models.petData.PetResponse

fun PetResponse.mapPetsDataItems(): PetDataModel {
    return PetDataModel(
        name = this.breeds[0].name,
        origin = this.breeds[0].origin,
        imageId = this.id,
        url = this.url
    )
}

fun FavouritePetsItem.mapFavPetsDataItem(): PetDataModel {
    return PetDataModel(
        favId = this.id,
        url = this.image.url,
        imageId = this.imageId
    )
}

fun SuccessResponse.mapSuccessData(): CallSuccessModel {
    return CallSuccessModel(
        successMessage = this.message,
        id = this.id
    )
}