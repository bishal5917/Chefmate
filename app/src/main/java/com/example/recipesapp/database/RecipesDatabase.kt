package com.example.recipesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipesapp.utils.entities.FavouritesEntity

@Database(
    entities = [FavouritesEntity::class],
    version = 1,
    exportSchema = false
)
//@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}