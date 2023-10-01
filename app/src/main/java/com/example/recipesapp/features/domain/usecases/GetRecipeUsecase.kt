package com.example.recipesapp.features.domain.usecases

import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.features.domain.repositories.UserRepository

class GetRecipeUsecase(private val userRepository: UserRepository) :
    Usecase<RecipeResponseModel, Unit> {
    override suspend fun call(params: Unit): RecipeResponseModel {
        return userRepository.getAllRecipes()
    }
}