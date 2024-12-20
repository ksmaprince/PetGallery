package com.khun.petgallery.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khun.petgallery.data.database.entity.FavImageEntity

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavPetImageRelation(favImageEntity: FavImageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavPetImageRelation(favImageEntity: List<FavImageEntity>): List<Long>

    @Query("SELECT favouriteId from favourite_details WHERE imageId=:imageId")
    suspend fun getFavId(imageId: String?): Int?

    @Query("DELETE FROM favourite_details where imageId=:imgId")
    suspend fun deleteFavImage(imgId: String): Int

    @Query("DELETE FROM favourite_details")
    suspend fun clearTable(): Int
}