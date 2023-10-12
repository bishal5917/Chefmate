package com.example.recipesapp.features.data.repositories

import com.example.recipesapp.features.data.datasource.LocalDatasource
import com.example.recipesapp.features.data.datasource.UserRemoteDatasource
import com.example.recipesapp.features.data.models.recipe_detail.RecipeDetailResponseModel
import com.example.recipesapp.features.data.models.recipe_detail.RecipeRequestModel
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.features.domain.repositories.UserRepository
import com.example.recipesapp.utils.Resource
import com.example.recipesapp.utils.entities.FavouritesEntity
import com.example.recipesapp.utils.models.QueryRequestModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.internal.wait
import retrofit2.Response

class UserRepositoryImpl(
    private val userRemoteDatasource: UserRemoteDatasource,
    private val localDatasource: LocalDatasource
) : UserRepository {
    override suspend fun getRecipes(queryRequestModel: QueryRequestModel): Resource<RecipeResponseModel> {
        return processResponse(userRemoteDatasource.getRecipes(queryRequestModel))
    }

    override suspend fun getRecipeDetail(recipeRequestModel: RecipeRequestModel): Resource<RecipeDetailResponseModel> {
        return processRecipeDetailResponse(userRemoteDatasource.getRecipeDetail(recipeRequestModel))
    }

    override fun getFavouriteRecipes(): Flow<List<FavouritesEntity>> {
        return localDatasource.getFavouriteRecipes()
    }

    override suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity) {
        return localDatasource.insertFavouriteRecipe(favouritesEntity)
    }

    override suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity) {
        return localDatasource.deleteFavouriteRecipe(favouritesEntity)
    }

    override suspend fun deleteAllFavouriteRecipe() {
        return localDatasource.deleteAllFavouriteRecipe()
    }

    private fun processRecipeDetailResponse(response: Response<RecipeDetailResponseModel>):
            Resource<RecipeDetailResponseModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
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