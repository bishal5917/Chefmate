package com.example.recipesapp.features.presentation.recipe_detail.viewmodel

import com.example.recipesapp.features.data.models.recipe_detail.RecipeRequestModel

sealed class RecipeDetailEvent {
    data class GetRecipeDetail(val recipeRequestModel: RecipeRequestModel) : RecipeDetailEvent()
}