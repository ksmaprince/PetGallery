package com.khun.petgallery.domain.mappers

data class PetDataModel(
    val name: String? = "",
    val origin: String? = "",
    val favId: Int = 0,
    val imageId: String,
    val url: String,
)