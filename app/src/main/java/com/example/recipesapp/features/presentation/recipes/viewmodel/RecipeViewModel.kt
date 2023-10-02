package com.example.recipesapp.features.presentation.recipes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.features.domain.usecases.GetRecipeUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val getRecipeUsecase: GetRecipeUsecase) :
    ViewModel() {

    private val _recipeState = MutableLiveData<RecipeState>(RecipeState(RecipeState.Status.IDLE))
    val recipeState : LiveData<RecipeState> = _recipeState

    fun onEvent(event: RecipeEvent) {
        when (event) {
            is RecipeEvent.GetRecipes -> {
                getRecipes()
            }
        }
    }

    private fun getRecipes() = viewModelScope.launch {
        _recipeState.postValue(
            _recipeState.value?.copy(
                status = RecipeState.Status.LOADING, message = "Getting Recipes..."
            )
        )
        try {
            val result = getRecipeUsecase.call(Unit)
            _recipeState.postValue(
                _recipeState.value?.copy(
                    status = RecipeState.Status.SUCCESS,
                    message = "Recipes Fetched...",
                    recipes = result
                )
            )
        } catch (ex: Exception) {
            _recipeState.postValue(
                _recipeState.value?.copy(
                    status = RecipeState.Status.FAILED, message = ex.localizedMessage
                )
            )
            Log.d("EX", "Exception: ${ex.message}")
        }
    }
}