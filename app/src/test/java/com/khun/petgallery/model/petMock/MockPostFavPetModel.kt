package com.khun.petgallery.model.petMock

import com.google.gson.annotations.SerializedName
import com.khun.petgallery.data.models.petDetails.PostFavPetModel
import com.khun.petgallery.utils.Constants

data class MockPostFavCatModel(
    @SerializedName("image_id")
    val imageId: String = "mi5",
    @SerializedName("sub_id")
    val subId: String = Constants.SUB_ID
)

fun toRequestPostFavPetData(mockPostFavCatModel: MockPostFavCatModel): PostFavPetModel {
    return PostFavPetModel(mockPostFavCatModel.imageId, mockPostFavCatModel.subId)
}