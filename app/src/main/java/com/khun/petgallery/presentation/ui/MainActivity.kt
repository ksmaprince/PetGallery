package com.khun.petgallery.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityOptionsCompat
import com.khun.petgallery.presentation.ui.features.pets.view.PetScreen
import com.khun.petgallery.presentation.ui.features.pets.viewmodel.PetsViewModel
import com.khun.petgallery.presentation.ui.theme.PetGalleryTheme
import com.khun.petgallery.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: PetsViewModel by viewModels()
    private val myActivityResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.getFavPetsData()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetGalleryTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                PetsDestination()
            }
        }
    }

    @Composable
    fun PetsDestination() {
        PetScreen(
            state = viewModel.state.collectAsState().value,
            effectFlow = viewModel.effects.receiveAsFlow(),
            onNavigationRequested = { itemUrl, imageId ->
                myActivityResultContract.launch(
                    Intent(this@MainActivity, PetFullImageActivity::class.java)
                        .apply {
                            putExtra(
                                Constants.URL,
                                itemUrl
                            )
                            putExtra(Constants.IMAGE_ID, imageId)
                        }, ActivityOptionsCompat.makeSceneTransitionAnimation(this)
                )
            },
            onRefreshCall = {
                viewModel.getPetsData()
            }
        )
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PetGalleryTheme {
        Greeting("Android")
    }
}