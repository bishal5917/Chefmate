package com.example.recipesapp.database

import androidx.room.TypeConverter
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun resultToString(result: RecipeResponseModel.Recipe): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): RecipeResponseModel.Recipe {
        val listType = object : TypeToken<RecipeResponseModel.Recipe>() {}.type
        return gson.fromJson(data, listType)
    }
}