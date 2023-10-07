package com.example.recipesapp.features.presentation.recipe_detail.viewmodel

import com.example.recipesapp.features.data.models.recipe_detail.RecipeDetailResponseModel

data class RecipeDetailState(
    val status: RecipeDetailStatus,
    val message: String? = null,
    val recipeDetail: RecipeDetailResponseModel? = null
) {
    companion object {
        val idle = RecipeDetailState(RecipeDetailStatus.IDLE, message = "Idle")
    }

    enum class RecipeDetailStatus {
        IDLE, LOADING, SUCCESS, FAILED
    }
}