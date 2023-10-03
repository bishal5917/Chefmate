package com.example.recipesapp.features.domain.repositories

import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.utils.Resource
import com.example.recipesapp.utils.models.QueryRequestModel

interface UserRepository {
    suspend fun getRecipes(queryRequestModel: QueryRequestModel) : Resource<RecipeResponseModel>
}