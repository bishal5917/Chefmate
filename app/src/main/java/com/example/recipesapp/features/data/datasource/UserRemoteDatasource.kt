package com.example.recipesapp.features.data.datasource

import com.example.recipesapp.core.configs.ApiConfig
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.services.network.ApiHandler
import com.example.recipesapp.services.network.ApiService
import com.google.gson.Gson
import okhttp3.internal.wait
import retrofit2.Response
import javax.inject.Inject

interface UserRemoteDatasource {
    suspend fun getRecipes(): Response<RecipeResponseModel>
}

class UserRemoteDatasourceImpl @Inject constructor(private val apiService: ApiService): UserRemoteDatasource {
    override suspend fun getRecipes(): Response<RecipeResponseModel> {
        return apiService.getRecipes()
    }

}