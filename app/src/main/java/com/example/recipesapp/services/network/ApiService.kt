package com.example.recipesapp.services.network

import com.example.recipesapp.core.configs.ApiConfig
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Get recipes
    @GET("/recipes/complexSearch")
    @JvmSuppressWildcards
    suspend fun getRecipes(
        @Header("x-api-key") authToken: String = ApiConfig.apiKey,
        @QueryMap queryMap: Map<String, Any>
    ): Response<RecipeResponseModel>
}