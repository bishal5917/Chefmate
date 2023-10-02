package com.example.recipesapp.features.domain.repositories

import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.utils.Resource

interface UserRepository {
    suspend fun getRecipes() : Resource<RecipeResponseModel>
}