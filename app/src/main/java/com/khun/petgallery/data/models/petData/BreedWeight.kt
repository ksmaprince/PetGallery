package com.khun.petgallery.data.models.petData

import com.google.gson.annotations.SerializedName

data class BreedWeight(

    @SerializedName("imperial") var imperial: String? = null,
    @SerializedName("metric") var metric: String? = null

)
