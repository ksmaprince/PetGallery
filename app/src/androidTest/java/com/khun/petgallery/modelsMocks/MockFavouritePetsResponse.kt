package com.khun.petgallery.modelsMocks

import com.google.gson.annotations.SerializedName
import com.khun.petgallery.data.models.petData.FavouritePetsItem
import com.khun.petgallery.data.models.petData.Image
import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.domain.mappers.PetDataModel
import retrofit2.Response


data class MockFavouritePetsResponse(
    @SerializedName("created_at")
    val createdAt: String = "2023-10-22T22:13:49.000Z",
    val id: Int = 232,
    val image: Image = toResponseFavPetImage(MockFavPetImage()),
    @SerializedName("image_id")
    val imageId: String = "5i",
    @SerializedName("sub_id")
    val subId: String = "my-user",
    @SerializedName("user_id")
    val userId: String = "4"
)

fun toResponseApiFavPets(mocksFavPetsDataModel: MockFavouritePetsResponse): Response<List<FavouritePetsItem>> {
    return Response.success(
        listOf(
            FavouritePetsItem(
                mocksFavPetsDataModel.createdAt,
                mocksFavPetsDataModel.id,
                mocksFavPetsDataModel.image,
                mocksFavPetsDataModel.imageId,
                mocksFavPetsDataModel.subId,
                mocksFavPetsDataModel.userId
            )
        )
    )
}

fun toResponseFavPets(mocksFavPetsDataModel: MockFavouritePetsResponse): NetworkResult<List<PetDataModel>> {
    return NetworkResult.Success(
        listOf(
            PetDataModel(
                favId = mocksFavPetsDataModel.id,
                url = mocksFavPetsDataModel.image.url,
                imageId = mocksFavPetsDataModel.imageId
            )
        )
    )
}

data class MockFavPetImage(
    val id: String = "5i",
    val url: String = "https://cdn2.thecatapi.com/images/5i4.jpg"
)

fun toResponseFavPetImage(mockFavPetImage: MockFavPetImage): Image {
    return Image(mockFavPetImage.id, mockFavPetImage.url)
}