package com.khun.petgallery.model.petMock

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
    val image: Image = toResponseFavCatImage(MockFavCatImage()),
    @SerializedName("image_id")
    val imageId: String = "5i",
    @SerializedName("sub_id")
    val subId: String = "my-user",
    @SerializedName("user_id")
    val userId: String = "4"
)

fun toResponseApiFavPets(mocksFavCatsDataModel: MockFavouritePetsResponse): Response<List<FavouritePetsItem>> {
    return Response.success(
        listOf(
            FavouritePetsItem(
                mocksFavCatsDataModel.createdAt,
                mocksFavCatsDataModel.id,
                mocksFavCatsDataModel.image,
                mocksFavCatsDataModel.imageId,
                mocksFavCatsDataModel.subId,
                mocksFavCatsDataModel.userId
            )
        )
    )
}

fun toResponseFavCats(mocksFavCatsDataModel: MockFavouritePetsResponse): NetworkResult<List<PetDataModel>> {
    return NetworkResult.Success(
        listOf(
            PetDataModel(
                favId = mocksFavCatsDataModel.id,
                url = mocksFavCatsDataModel.image.url,
                imageId = mocksFavCatsDataModel.imageId
            )
        )
    )
}

data class MockFavCatImage(
    val id: String = "5i",
    val url: String = "https://cdn2.thecatapi.com/images/5i4.jpg"
)

fun toResponseFavCatImage(mockFavCatImage: MockFavCatImage): Image {
    return Image(mockFavCatImage.id, mockFavCatImage.url)
}