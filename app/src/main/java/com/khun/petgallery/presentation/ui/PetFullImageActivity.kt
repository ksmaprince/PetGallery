package com.khun.petgallery.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.khun.petgallery.presentation.ui.features.petDetails.view.PetFullDetail
import com.khun.petgallery.presentation.ui.features.petDetails.viewModel.PetsDetailsViewModel
import com.khun.petgallery.presentation.ui.theme.PetGalleryTheme
import com.khun.petgallery.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetFullImageActivity : ComponentActivity() {
    private var initialState: Boolean = false
    private val viewModel: PetsDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(Constants.IMAGE_ID)?.let { viewModel.checkFav(it) }
        setContent {
            PetGalleryTheme {
                PetsFullView()
            }
        }
    }


    @Composable
    private fun PetsFullView() {
        initialState = remember { viewModel.isFavourite.value }
        val isFavourite by viewModel.isFavourite.collectAsState()
        PetFullDetail(
            url = intent.getStringExtra(Constants.URL),
            onBackPressed = {
                if (initialState != isFavourite) {
                    val resultIntent = Intent()
                    resultIntent.putExtra(Constants.FAV_ID, viewModel.favId)
                    setResult(RESULT_OK, resultIntent)
                }
                onBackPressedDispatcher.onBackPressed()
            },
            isFavourite = isFavourite,
            favSelection = {
                viewModel.updateFavouriteState(it)
                if (it) {
                    viewModel.postFavPetData()
                } else viewModel.deleteFavPetData()
            }
        )

    }

}
