package com.example.recipesapp.features.presentation.recipes.viewmodel

import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel

data class RecipeState(
    val status: RecipeStatus,
    val message: String? = null,
    val mealType: String? = null,
    val dietType: String? = null,
    val recipes: RecipeResponseModel? = null
) {
    companion object {
        val idle = RecipeState(RecipeStatus.IDLE, message = "Idle")
    }

    enum class RecipeStatus {
        IDLE, LOADING, SUCCESS, FAILED
    }
}