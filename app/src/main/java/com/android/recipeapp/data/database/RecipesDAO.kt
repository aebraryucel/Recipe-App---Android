package com.android.recipeapp.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavouriteRecipe(recipe:FavouriteRecipeEntity)


    @Delete
    suspend fun deleteFavouriteRecipe(recipe:FavouriteRecipeEntity)


    @Query("DELETE FROM table_favourite_recipes")
    suspend fun deleteAllFavouriteRecipes()


    @Query("SELECT * FROM table_recipes")
    fun getRecipes(): Flow<List<RecipesEntity>>

    @Query("SELECT * FROM table_favourite_recipes")
    fun getFavouriteRecipes() : Flow<List<FavouriteRecipeEntity>>






}