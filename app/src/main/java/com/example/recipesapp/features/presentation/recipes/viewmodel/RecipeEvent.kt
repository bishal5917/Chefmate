package com.example.recipesapp.features.presentation.recipes.viewmodel

import com.example.recipesapp.utils.models.QueryRequestModel

sealed class RecipeEvent {
    data class GetRecipes(val queryRequestModel: QueryRequestModel) : RecipeEvent()
}