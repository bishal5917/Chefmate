package com.example.recipesapp.features.domain.usecases

import android.util.Log
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.features.domain.repositories.UserRepository
import com.example.recipesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetRecipeUsecase(private val userRepository: UserRepository) {

    fun call(): Flow<Resource<RecipeResponseModel>> = flow {
        emit(Resource.Loading())
        try {
            val response = userRepository.getRecipes()
            Log.d("Usecase", "API Response, ${response.data}")
            emit(response)
        } catch (e: HttpException) {
            Log.i("Usecase", e.localizedMessage!!)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            Log.i("Usecase", e.localizedMessage!!)
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}