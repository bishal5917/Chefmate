package com.example.recipesapp.features.presentation.recipes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.features.domain.usecases.GetRecipeUsecase
import com.example.recipesapp.utils.Network.isNetworkAvailable
import com.example.recipesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val getRecipeUsecase: GetRecipeUsecase) :
    ViewModel() {

    private val _recipeState = MutableLiveData<RecipeState>(RecipeState(RecipeState.Status.IDLE))
    val recipeState: LiveData<RecipeState> = _recipeState

    fun onEvent(event: RecipeEvent) {
        when (event) {
            is RecipeEvent.GetRecipes -> {
                getRecipes()
            }
        }
    }

    init {
        getRecipes()
    }

    private fun getRecipes() {
        getRecipeUsecase.call().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _recipeState.postValue(
                        _recipeState.value?.copy(
                            status = RecipeState.Status.LOADING, message = "Getting Recipes..."
                        )
                    )
                }
                is Resource.Success -> {
                    _recipeState.postValue(
                        _recipeState.value?.copy(
                            status = RecipeState.Status.LOADING, message = "Getting Recipes...",
                            recipes = result.data,
                        )
                    )
                }
                is Resource.Error -> {
                    _recipeState.postValue(
                        _recipeState.value?.copy(
                            status = RecipeState.Status.LOADING, message = result.message.toString()
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
