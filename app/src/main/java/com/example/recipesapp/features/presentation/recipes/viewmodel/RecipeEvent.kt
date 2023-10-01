package com.example.recipesapp.features.presentation.recipes.viewmodel

sealed class RecipeEvent {
    object GetRecipes : RecipeEvent()
}