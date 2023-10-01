package com.example.recipesapp.features.domain.repositories

import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel

interface UserRepository {
    suspend fun getAllRecipes(): RecipeResponseModel
}