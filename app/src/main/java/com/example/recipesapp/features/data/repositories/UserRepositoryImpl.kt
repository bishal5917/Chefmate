package com.example.recipesapp.features.data.repositories

import com.example.recipesapp.features.data.datasource.UserRemoteDatasource
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.features.domain.repositories.UserRepository
import com.google.gson.Gson
import okhttp3.internal.wait

class UserRepositoryImpl(private val userRemoteDatasource: UserRemoteDatasource) : UserRepository {
    private val gson = Gson()
    override suspend fun getAllRecipes(): RecipeResponseModel {
        try {
            val result = userRemoteDatasource.getAllRecipes().wait()
            return gson.fromJson(result.toString(), RecipeResponseModel::class.java)
        } catch (ex: Exception) {
            throw ex
        }
    }
}