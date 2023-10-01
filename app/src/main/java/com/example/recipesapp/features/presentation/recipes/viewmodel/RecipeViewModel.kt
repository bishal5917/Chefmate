package com.example.recipesapp.features.presentation.recipes.viewmodel

import android.util.Log
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

    private val _recipeState = MutableStateFlow(RecipeState.IDLE)
    val recipeState = _recipeState.asStateFlow()

    fun onEvent(event: RecipeEvent) {
        when (event) {
            is RecipeEvent.GetRecipes -> {
                getRecipes()
            }
        }
    }

    private fun getRecipes() = viewModelScope.launch {
        _recipeState.value = _recipeState.value.copy(
            status = RecipeState.Status.LOADING, message = "Getting Recipes..."
        )
        try {
            val result = getRecipeUsecase.call(Unit)
            _recipeState.value = _recipeState.value.copy(
                status = RecipeState.Status.SUCCESS, message = "Recipes fetched", recipes = result,
            )
        } catch (ex: Exception) {
            _recipeState.value = _recipeState.value.copy(
                status = RecipeState.Status.FAILED, message = "${ex.message}"
            )
            Log.d("EX", "Exception: ${ex.message}")
        }
    }
}