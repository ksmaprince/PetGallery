package com.khun.petgallery.presentation.ui.features.pets.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.khun.petgallery.data.database.AppDatabase
import com.khun.petgallery.data.database.pets.PetsDatabaseHelperImpl
import com.khun.petgallery.data.models.petData.PetResponse
import com.khun.petgallery.data.remote.PetsService
import com.khun.petgallery.data.remote.pets.PetApiServiceHelperImpl
import com.khun.petgallery.data.repositories.PetsRepositoryImpl
import com.khun.petgallery.domain.repositories.PetRepository
import com.khun.petgallery.domain.usecase.pets.GetFavPetsUseCaseImpl
import com.khun.petgallery.domain.usecase.pets.GetPetsUseCaseImpl
import com.khun.petgallery.model.petMock.MocksPetsDataModel
import com.khun.petgallery.model.petMock.toResponseApiPets
import com.khun.petgallery.model.petMock.toResponsePets
import junit.framework.TestCase.assertEquals
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
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PetsViewModelTest {
    private lateinit var mPetsRepo: PetRepository
    private val application: Application = mock()
    private lateinit var mViewModel: PetsViewModel

    @get:Rule
    val testInstantTaskExecutorRules: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var petService: PetsService

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val databaseReference = mock(AppDatabase::class.java)
        val apiHelper = PetApiServiceHelperImpl(petService)
        val dbHelper = PetsDatabaseHelperImpl(databaseReference)
        mPetsRepo = PetsRepositoryImpl(apiHelper, dbHelper)
        Dispatchers.setMain(testDispatcher)
        val petsUseCase = GetPetsUseCaseImpl(mPetsRepo)
        val favPetUseCase = GetFavPetsUseCaseImpl(mPetsRepo)
        mViewModel = PetsViewModel(petsUseCase, favPetUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetEmptyData() = runTest(UnconfinedTestDispatcher()) {
        val expectedRepositories = retrofit2.Response.success(listOf<PetResponse>())
        `when`(petService.fetchPetsImages(0)).thenReturn(expectedRepositories)
        val result = petService.fetchPetsImages(0)
        verify(petService).fetchPetsImages(0)
        assert(result == expectedRepositories)
    }

    @Test
    fun testGetPetsApiData() = runTest(UnconfinedTestDispatcher()){
        val mockPetsData = MocksPetsDataModel()
        val response = toResponseApiPets(mockPetsData)
        val verifyData = toResponsePets(mockPetsData)

        `when`(petService.fetchPetsImages(10)).thenReturn(response)
        verify(petService).fetchPetsImages(10)
        mViewModel.getPetsData()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = mViewModel.state.value.pets
        assertEquals("Both are not equal", result.size, verifyData.size)
        assertEquals("Both are not equal", result[0].url, verifyData[0].url)
    }
}