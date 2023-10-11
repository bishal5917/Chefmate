package com.example.recipesapp.features.data.datasource

import com.example.recipesapp.database.RecipesDao
import com.example.recipesapp.utils.entities.FavouritesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDatasource @Inject constructor(private val recipesDao: RecipesDao) {

    fun getFavouriteRecipes(): Flow<List<FavouritesEntity>> {
        return recipesDao.getFavouriteRecipes()
    }

    suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity) {
        return recipesDao.insertFavouriteRecipe(favouritesEntity)
    }

    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity) {
        return recipesDao.deleteFavouriteRecipe(favouritesEntity)
    }

    suspend fun deleteAllFavouriteRecipe() {
        return recipesDao.deleteAllFavouriteRecipe()
    }
}