package com.khun.petgallery.presentation.ui.features.petDetails.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.domain.usecase.petsDetails.CheckFavUseCase
import com.khun.petgallery.domain.usecase.petsDetails.DeleteFavPetUseCase
import com.khun.petgallery.domain.usecase.petsDetails.PostFavPetUseCase
import com.khun.petgallery.presentation.contracts.BaseContract
import com.khun.petgallery.presentation.contracts.PetDetailsContract
import com.khun.petgallery.utils.ErrorsMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetsDetailsViewModel @Inject constructor(
    private val postFavPetUseCase: PostFavPetUseCase,
    private val deleteFavPetUseCase: DeleteFavPetUseCase,
    private val checkFavouriteUseCase: CheckFavUseCase,
) : ViewModel() {

    var state by mutableStateOf(
        PetDetailsContract.State(
            postFavPetSuccess = null,
            deleteFavPetSuccess = null,
        )
    )
        private set

    private val _isFavourite = MutableStateFlow(false)
    var isFavourite = _isFavourite.asStateFlow()
    fun updateFavouriteState(newValue: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavourite.value = newValue
        }
    }

    private lateinit var imageId: String
    var favId: Int = 0

    var effects = Channel<BaseContract.Effect>(Channel.UNLIMITED)
        private set

    fun checkFav(imageId: String) {
        this.imageId = imageId
        viewModelScope.launch(Dispatchers.IO) {
            checkFavouriteUseCase.execute(imageId).collect {
                _isFavourite.value = it != null && it != 0
                if (it != null) {
                    favId = it
                }
            }
        }


    }


    fun postFavPetData() {
        viewModelScope.launch(Dispatchers.IO) {
            postFavPetUseCase.execute(imageId).collect {
                when (it) {
                    is NetworkResult.Success -> {
                        favId = it.data?.id ?: favId
                        state = state.copy(postFavPetSuccess = it.data!!)
                        _isFavourite.value = true
                        effects.send(BaseContract.Effect.DataWasLoaded)
                    }

                    is NetworkResult.Error -> {
                        effects.send(
                            BaseContract.Effect.Error(
                                it.message ?: ErrorsMessage.gotApiCallError
                            )
                        )
                    }

                    else -> {}
                }
            }

        }
    }

    fun deleteFavPetData() {
        if (isFavourite.value)
            viewModelScope.launch(Dispatchers.IO) {
                deleteFavPetUseCase.execute(imageId, favId).collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            state = state.copy(deleteFavPetSuccess = it.data!!)
                            _isFavourite.value = false
                            effects.send(BaseContract.Effect.DataWasLoaded)
                        }

                        is NetworkResult.Error -> {
                            effects.send(
                                BaseContract.Effect.Error(
                                    it.message ?: ErrorsMessage.gotApiCallError
                                )
                            )
                        }

                        else -> {}
                    }
                }

            }
    }


}