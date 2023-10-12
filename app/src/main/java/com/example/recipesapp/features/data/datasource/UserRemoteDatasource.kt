package com.example.recipesapp.features.data.datasource

import com.example.recipesapp.core.configs.ApiConfig
import com.example.recipesapp.features.data.models.food_joke.FoodJokeResponseModel
import com.example.recipesapp.features.data.models.recipe_detail.RecipeDetailResponseModel
import com.example.recipesapp.features.data.models.recipe_detail.RecipeRequestModel
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.services.network.ApiHandler
import com.example.recipesapp.services.network.ApiService
import com.example.recipesapp.utils.GetQuery
import com.example.recipesapp.utils.models.QueryRequestModel
import com.google.gson.Gson
import okhttp3.internal.wait
import retrofit2.Response
import javax.inject.Inject

interface UserRemoteDatasource {
    suspend fun getRecipes(queryRequestModel: QueryRequestModel): Response<RecipeResponseModel>
    suspend fun getRecipeDetail(recipeRequestModel: RecipeRequestModel): Response<RecipeDetailResponseModel>
    suspend fun getJoke(): Response<FoodJokeResponseModel>
}

class UserRemoteDatasourceImpl @Inject constructor(private val apiService: ApiService) :
    UserRemoteDatasource {
    override suspend fun getRecipes(queryRequestModel: QueryRequestModel): Response<RecipeResponseModel> {
        return apiService.getRecipes(
            queryMap = GetQuery.getQueryMap(queryRequestModel)
        )
    }

    override suspend fun getRecipeDetail(recipeRequestModel: RecipeRequestModel): Response<RecipeDetailResponseModel> {
        return apiService.getRecipeDetail(
            recipeId = recipeRequestModel.recipeId
        )
    }

    override suspend fun getJoke(): Response<FoodJokeResponseModel> {
        return apiService.getJoke()
    }
}