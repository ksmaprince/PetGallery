package com.khun.petgallery.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.khun.petgallery.data.database.AppDatabase
import com.khun.petgallery.data.database.dao.FavouriteDao
import com.khun.petgallery.data.database.entity.FavImageEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class PetItemDaoTest {

    private lateinit var petFavDao: FavouriteDao
    private lateinit var appDb: AppDatabase

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDb = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
        petFavDao = appDb.favImageDao()
    }

    @After
    fun tearDown() {
        appDb.close()
    }

    private fun getEntityData(id: Int) : FavImageEntity {
        return FavImageEntity(
            favouriteId = id,
            imageId = "img$id"
        )
    }

    @Test
    fun addPet_shouldReturn_thePet() = runTest {
        val item1 = getEntityData(1)
        val item2 = getEntityData(2)

        val insertId1 = petFavDao.insertFavPetImageRelation(item1)
        val insertId2 = petFavDao.insertFavPetImageRelation(item2)

        assert(insertId1 > 0)
        assert(insertId2 > 0)

        val retrievedFavId1 = petFavDao.getFavId("img1")
        val retrievedFavId2 = petFavDao.getFavId("img2")

        assert(retrievedFavId1 == 1)
        assert(retrievedFavId2 == 2)
    }
}