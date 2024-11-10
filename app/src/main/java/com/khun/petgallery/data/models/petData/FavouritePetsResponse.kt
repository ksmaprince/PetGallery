package com.khun.petgallery.data.models.petData

import com.google.gson.annotations.SerializedName


data class FavouritePetsItem(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    val image: Image,
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("sub_id")
    val subId: String,
    @SerializedName("user_id")
    val userId: String
)

data class Image(
    val id: String,
    val url: String
)