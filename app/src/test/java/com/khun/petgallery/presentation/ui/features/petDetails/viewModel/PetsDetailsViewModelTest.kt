package com.khun.petgallery.presentation.ui.features.petDetails.viewModel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.khun.petgallery.data.database.AppDatabase
import com.khun.petgallery.data.database.petsDetails.PetsDetailsDatabaseHelperImpl
import com.khun.petgallery.data.models.SuccessResponse
import com.khun.petgallery.data.remote.PetsService
import com.khun.petgallery.data.remote.petsDetail.PetDetailsApiServiceHelperImpl
import com.khun.petgallery.data.repositories.PetsDetailsRepositoryImpl
import com.khun.petgallery.domain.repositories.PetDetailsRepository
import com.khun.petgallery.domain.usecase.petsDetails.CheckFavUseCaseImpl
import com.khun.petgallery.domain.usecase.petsDetails.DeleteFavPetUseCaseImpl
import com.khun.petgallery.domain.usecase.petsDetails.PostFavPetUseCaseImpl
import com.khun.petgallery.model.petMock.MockPostFavPetModel
import com.khun.petgallery.model.petMock.MockSuccessResponse
import com.khun.petgallery.model.petMock.toRequestPostFavPetData
import com.khun.petgallery.model.petMock.toResponsePostSuccess
import com.khun.petgallery.utils.TestTags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class PetsDetailsViewModelTest {
    private lateinit var mPetsRepo: PetDetailsRepository
    private val application: Application = mock()
    private lateinit var mViewModel: PetsDetailsViewModel

    @get:Rule
    val testInstanTaskExecutorRules: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var petsService: PetsService

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val databaseReference = mock(AppDatabase::class.java)
        val apiHelper = PetDetailsApiServiceHelperImpl(petsService)
        val dbHelper = PetsDetailsDatabaseHelperImpl(databaseReference)
        mPetsRepo = PetsDetailsRepositoryImpl(apiHelper, dbHelper)
        Dispatchers.setMain(testDispatcher)
        val postFavPetUseCase = PostFavPetUseCaseImpl(mPetsRepo)
        val deleteFavPetUseCase = DeleteFavPetUseCaseImpl(mPetsRepo)
        val checkFavouriteUseCase = CheckFavUseCaseImpl(mPetsRepo)

        mViewModel =
            PetsDetailsViewModel(postFavPetUseCase, deleteFavPetUseCase, checkFavouriteUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test postFavCatData Success`() = runTest (UnconfinedTestDispatcher()){
        val postFavPetsDataModel = toRequestPostFavPetData(MockPostFavPetModel())
        val expectedResponse = toResponsePostSuccess(MockSuccessResponse())
        `when`(petsService.postFavouritePet(postFavPetsDataModel)).thenReturn(expectedResponse)
        val response = petsService.postFavouritePet(postFavPetsDataModel)
        assert(response.isSuccessful)
        assert(response.code() == 200)
        assert(response.body() == expectedResponse.body())
    }

    @Test
    fun `test deleteFavCat success`() = runTest (UnconfinedTestDispatcher()){
        val expectedResponse = Response.success(SuccessResponse(0, message = "SUCCESS"))
        `when`(petsService.deleteFavouritePet(TestTags.FAV_ID)).thenReturn(expectedResponse)
        val response = petsService.deleteFavouritePet(TestTags.FAV_ID)
        assert(response.isSuccessful)
        assert(response.code() == 200)
        assert(response.body() == expectedResponse.body())
    }
}