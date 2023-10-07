package com.example.recipesapp.features.domain.usecases

import android.util.Log
import com.example.recipesapp.features.data.models.recipe_detail.RecipeDetailResponseModel
import com.example.recipesapp.features.data.models.recipe_detail.RecipeRequestModel
import com.example.recipesapp.features.domain.repositories.UserRepository
import com.example.recipesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetRecipeDetailUsecase(private val userRepository: UserRepository) {
    fun call(recipeRequestModel: RecipeRequestModel): Flow<Resource<RecipeDetailResponseModel>> = flow {
        emit(Resource.Loading())
        try {
            val response = userRepository.getRecipeDetail(recipeRequestModel)
            Log.d("Usecase", "API Response, ${response.message}")
            Log.d("Usecase", "API Response, ${response.data}")
            emit(response)
        } catch (e: HttpException) {
            Log.d("Usecase", e.localizedMessage!!)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            Log.d("Usecase", e.localizedMessage!!)
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}