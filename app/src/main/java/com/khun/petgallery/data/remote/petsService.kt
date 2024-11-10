package com.khun.petgallery.data.remote

import com.khun.petgallery.data.models.SuccessResponse
import com.khun.petgallery.data.models.petData.FavouritePetsItem
import com.khun.petgallery.data.models.petData.PetResponse
import com.khun.petgallery.data.models.petDetails.PostFavPetModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface petsService {

    @GET("v1/images/search?size=small&has_breeds=true&order=RANDOM&page=0")
    suspend fun fetchPetsImages(
        @Query("limit") limit: Int
    ): Response<List<PetResponse>>

    @GET("v1/favourites")
    suspend fun fetchFavouritePets(
        @Query("sub_id") subId: String
    ): Response<List<FavouritePetsItem>>

    @POST("v1/favourites")
    suspend fun postFavouritePet(
        @Body favCat: PostFavPetModel
    ): Response<SuccessResponse>

    @DELETE("v1/favourites/{favourite_id}")
    suspend fun deleteFavouritePet(
        @Path("favourite_id") favouriteId: Int
    ): Response<SuccessResponse>
}