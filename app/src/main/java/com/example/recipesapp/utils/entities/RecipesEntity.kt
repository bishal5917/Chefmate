package com.example.recipesapp.utils.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.utils.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipe: RecipeResponseModel.Recipe) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}