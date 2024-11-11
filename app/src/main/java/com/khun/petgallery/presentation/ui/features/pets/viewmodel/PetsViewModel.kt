package com.khun.petgallery.presentation.ui.features.pets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khun.petgallery.data.remote.NetworkResult
import com.khun.petgallery.domain.usecase.pets.GetFavPetsUseCase
import com.khun.petgallery.domain.usecase.pets.GetPetsUseCase
import com.khun.petgallery.presentation.contracts.BaseContract
import com.khun.petgallery.presentation.contracts.PetContract
import com.khun.petgallery.utils.ErrorsMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetsViewModel @Inject constructor(
    private val petsUseCase: GetPetsUseCase,
    private val petFavUseCase: GetFavPetsUseCase
) : ViewModel() {
    init {
        getPetsData()
        getFavPetsData()
    }

    private val _state = MutableStateFlow(
        PetContract.State(
            pets = listOf(),
            favPets = listOf(),
            isLoading = false
        )
    )
    val state: StateFlow<PetContract.State> = _state

    var effects = Channel<BaseContract.Effect>(Channel.UNLIMITED)
        private set

    private fun updateState(newState: PetContract.State) {
        _state.value = newState
    }

    fun getPetsData() {
        viewModelScope.launch(Dispatchers.IO) {
            petsUseCase.execute().collect {
                when (it) {
                    is NetworkResult.Success -> {
                        val newState = state.value.copy(
                            pets = it.data!!,
                            isLoading = false
                        )
                        updateState(newState)
                        effects.send(BaseContract.Effect.DataWasLoaded)
                    }

                    is NetworkResult.Error -> {
                        val newState = state.value.copy(isLoading = false)
                        updateState(newState)
                        effects.send(
                            BaseContract.Effect.Error(
                                it.message ?: ErrorsMessage.gotApiCallError
                            )
                        )
                    }

                    is NetworkResult.Loading -> {
                        if (!state.value.isLoading) {
                            val newState = state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }
                }
            }
        }
    }

    fun getFavPetsData() {
        viewModelScope.launch(Dispatchers.IO) {
            petFavUseCase.execute().collect {
                when (it) {
                    is NetworkResult.Success -> {
                        val newState = state.value.copy(
                            favPets = it.data!!,
                            isLoading = false
                        )
                        updateState(newState)
                        effects.send(BaseContract.Effect.DataWasLoaded)
                    }

                    is NetworkResult.Error -> {
                        val newState = state.value.copy(isLoading = false)
                        updateState(newState)
                        effects.send(
                            BaseContract.Effect.Error(
                                it.message ?: ErrorsMessage.gotApiCallError
                            )
                        )
                    }

                    is NetworkResult.Loading -> {
                        if (!state.value.isLoading){
                            val newState = state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }
                }
            }
        }
    }
}