package com.example.recipesapp.features.data.repositories

import com.example.recipesapp.features.data.datasource.UserRemoteDatasource
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.features.domain.repositories.UserRepository
import com.example.recipesapp.utils.Resource
import com.google.gson.Gson
import okhttp3.internal.wait
import retrofit2.Response

class UserRepositoryImpl(private val userRemoteDatasource: UserRemoteDatasource) : UserRepository {
    override suspend fun getRecipes(): Resource<RecipeResponseModel> {
        return processResponse(userRemoteDatasource.getRecipes())
    }

    private fun processResponse(response: Response<RecipeResponseModel>):
            Resource<RecipeResponseModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }
}