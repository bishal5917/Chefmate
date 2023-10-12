package com.example.recipesapp.features.data.datasource

import com.example.recipesapp.database.RecipesDao
import com.example.recipesapp.utils.entities.FavouritesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDatasource {
    fun getFavouriteRecipes(): Flow<List<FavouritesEntity>>
    suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity)
    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity)
    suspend fun deleteAllFavouriteRecipe()
}

class LocalDatasourceImpl @Inject constructor(private val recipesDao: RecipesDao) :
    LocalDatasource {

    override fun getFavouriteRecipes(): Flow<List<FavouritesEntity>> {
        return recipesDao.getFavouriteRecipes()
    }

    override suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity) {
        return recipesDao.insertFavouriteRecipe(favouritesEntity)
    }

    override suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity) {
        return recipesDao.deleteFavouriteRecipe(favouritesEntity)
    }

    override suspend fun deleteAllFavouriteRecipe() {
        return recipesDao.deleteAllFavouriteRecipe()
    }
}