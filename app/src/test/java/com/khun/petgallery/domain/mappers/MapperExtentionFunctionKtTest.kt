package com.khun.petgallery.domain.mappers

import com.khun.petgallery.data.models.SuccessResponse
import com.khun.petgallery.data.models.petData.FavouritePetsItem
import com.khun.petgallery.data.models.petData.PetResponse
import com.khun.petgallery.model.petMock.MockFavouritePetsResponse
import com.khun.petgallery.model.petMock.MockSuccessResponse
import com.khun.petgallery.model.petMock.MocksPetsDataModel
import org.junit.Assert.*
import org.junit.Test

class MapperExtentionFunctionKtTest {

    @Test
    fun mapPetsDataItems() {
        val petResponse = PetResponse(
            breeds = MocksPetsDataModel().breeds,
            height = 23,
            id = "img1",
            url = "www.image.com",
            width = 200
        )
        val finalResult = petResponse.mapPetsDataItems()
        assertEquals(petResponse.id, finalResult.imageId)
    }


    @Test
    fun mapFavCatsDataItems() {
        val mocksFavPetsDataModel = MockFavouritePetsResponse()
        val favPetResponse = FavouritePetsItem(
            mocksFavPetsDataModel.createdAt,
            mocksFavPetsDataModel.id,
            mocksFavPetsDataModel.image,
            mocksFavPetsDataModel.imageId,
            mocksFavPetsDataModel.subId,
            mocksFavPetsDataModel.userId
        )
        val finalResult = favPetResponse.mapFavPetsDataItem()
        assertEquals(favPetResponse.imageId, finalResult.imageId)
        assertEquals(favPetResponse.image.url, finalResult.url)
    }

    @Test
    fun mapSuccessData() {
        val mockSuccessResponse = MockSuccessResponse()
        val successResponse = SuccessResponse(mockSuccessResponse.id, mockSuccessResponse.message)
        val finalResult = successResponse.mapSuccessData()
        assertEquals(finalResult.id, successResponse.id)
        assertEquals(finalResult.successMessage, successResponse.message)
    }
}