package com.khun.petgallery.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khun.petgallery.data.database.dao.FavouriteDao
import com.khun.petgallery.data.database.entity.FavImageEntity

@Database(entities = [FavImageEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favImageDao(): FavouriteDao
}