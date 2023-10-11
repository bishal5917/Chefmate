package com.example.recipesapp.database

import androidx.room.*
import com.example.recipesapp.utils.entities.FavouritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity)

    @Query("Select * from favourite_recipes_table Order by id asc")
    fun getFavouriteRecipes(): Flow<List<FavouritesEntity>>

    @Delete
    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity)

    @Query("Delete from favourite_recipes_table")
    suspend fun deleteAllFavouriteRecipe()
}