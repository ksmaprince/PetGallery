package com.khun.petgallery.data.models.petDetails

import com.google.gson.annotations.SerializedName

data class PostFavPetModel(
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("sub_id")
    val subId: String
)