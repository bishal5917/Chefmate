package com.example.recipesapp.features.data.repositories

import com.example.recipesapp.features.data.datasource.UserRemoteDatasource
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.features.domain.repositories.UserRepository
import com.example.recipesapp.utils.Resource
import com.example.recipesapp.utils.models.QueryRequestModel
import com.google.gson.Gson
import okhttp3.internal.wait
import retrofit2.Response

class UserRepositoryImpl(private val userRemoteDatasource: UserRemoteDatasource) : UserRepository {
    override suspend fun getRecipes(queryRequestModel: QueryRequestModel): Resource<RecipeResponseModel> {
        return processResponse(userRemoteDatasource.getRecipes(queryRequestModel))
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