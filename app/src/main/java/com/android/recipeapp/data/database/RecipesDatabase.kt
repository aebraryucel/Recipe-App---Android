package com.android.recipeapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(RecipesTypeConverter::class)
@Database(entities = [RecipesEntity::class,FavouriteRecipeEntity::class], version = 1, exportSchema = false)
abstract class RecipesDatabase :RoomDatabase(){

    abstract fun recipesDao(): RecipesDAO

}