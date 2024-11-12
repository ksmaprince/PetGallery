package com.khun.petgallery.model.petMock

import com.khun.petgallery.data.models.petData.BreedWeight

data class MockWeight(
    val imperial: String = "23",
    val metric: String = "25"
)

fun toResponsePetBreedWeight(mockWeight: MockWeight): BreedWeight {
    return BreedWeight(mockWeight.imperial, mockWeight.metric)
}