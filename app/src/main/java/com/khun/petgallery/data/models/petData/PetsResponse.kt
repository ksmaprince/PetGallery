package com.khun.petgallery.data.models.petData


data class PetResponse(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)