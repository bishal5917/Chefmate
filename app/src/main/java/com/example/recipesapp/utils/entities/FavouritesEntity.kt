package com.example.recipesapp.utils.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.utils.Constants.FAVOURITE_RECIPES_TABLE

@Entity(tableName = FAVOURITE_RECIPES_TABLE)
class FavouritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
//    var result: RecipeResponseModel.Recipe
)