package com.example.recipesapp.features.data.datasource

import com.example.recipesapp.core.configs.ApiConfig
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.services.network.ApiHandler
import com.google.gson.Gson
import okhttp3.internal.wait

interface UserRemoteDatasource {
    suspend fun getAllRecipes(): RecipeResponseModel
}

class UserRemoteDatasourceImpl : UserRemoteDatasource {
    private val gson = Gson()
    override suspend fun getAllRecipes(): RecipeResponseModel {
        try {
            val response = ApiHandler().get(
                api = ApiConfig.recipesUrl + ApiConfig
                    .complexSearchUrl
            )?.wait()
            return gson.fromJson(response.toString(), RecipeResponseModel::class.java)
        } catch (ex: Exception) {
            throw  ex;
        }
    }
}