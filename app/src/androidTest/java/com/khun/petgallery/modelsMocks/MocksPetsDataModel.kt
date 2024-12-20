package com.khun.petgallery.modelsMocks

import com.khun.petgallery.data.models.petData.Breed
import com.khun.petgallery.data.models.petData.PetResponse
import com.khun.petgallery.domain.mappers.PetDataModel
import retrofit2.Response

data class MocksPetsDataModel(
    val breeds: List<Breed> = listOf(
        toResponsePetBread(MockBreed()),
        toResponsePetBread(MockBreed())
    ),
    val height: Int = 250,
    val id: String = "IDPet1",
    val url: String = "https://google.com/",
    val width: Int = 250
)

fun toResponseApiPets(mocksPetsDataModel: MocksPetsDataModel): Response<List<PetResponse>> {
    return Response.success(
        listOf(
            PetResponse(
                mocksPetsDataModel.breeds,
                mocksPetsDataModel.height,
                mocksPetsDataModel.id,
                mocksPetsDataModel.url,
                mocksPetsDataModel.width
            )
        )
    )
}

fun toResponsePets(mocksPetsDataModel: MocksPetsDataModel): List<PetDataModel> {
    return listOf(
        PetDataModel(
            imageId = mocksPetsDataModel.id,
            url = mocksPetsDataModel.url
        )
    )
}