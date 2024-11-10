package com.khun.petgallery.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_details")
data class FavImageEntity(
    val favouriteId: Int,
    @PrimaryKey
    val imageId: String
)
