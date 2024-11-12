package com.khun.petgallery.model.petMock

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
    val id: String = "IDCat1",
    val url: String = "https://google.com/",
    val width: Int = 250
)

fun toResponseApiPets(mocksCatsDataModel: MocksPetsDataModel): Response<List<PetResponse>> {
    return Response.success(
        listOf(
            PetResponse(
                mocksCatsDataModel.breeds,
                mocksCatsDataModel.height,
                mocksCatsDataModel.id,
                mocksCatsDataModel.url,
                mocksCatsDataModel.width
            )
        )
    )
}

fun toResponsePets(mocksCatsDataModel: MocksPetsDataModel): List<PetDataModel> {
    return listOf(
        PetDataModel(
            imageId = mocksCatsDataModel.id,
            url = mocksCatsDataModel.url
        )
    )
}