package com.example.recipesapp.features.domain.repositories

import com.example.recipesapp.features.data.models.recipe_detail.RecipeDetailResponseModel
import com.example.recipesapp.features.data.models.recipe_detail.RecipeRequestModel
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.utils.Resource
import com.example.recipesapp.utils.entities.FavouritesEntity
import com.example.recipesapp.utils.models.QueryRequestModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getRecipes(queryRequestModel: QueryRequestModel): Resource<RecipeResponseModel>
    suspend fun getRecipeDetail(recipeRequestModel: RecipeRequestModel):
            Resource<RecipeDetailResponseModel>
    fun getFavouriteRecipes(): Flow<List<FavouritesEntity>>
    suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity)
    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity)
    suspend fun deleteAllFavouriteRecipe()
}