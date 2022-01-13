package com.android.recipeapp.data

import com.android.recipeapp.data.database.FavouriteRecipeEntity
import com.android.recipeapp.data.database.RecipesDAO
import com.android.recipeapp.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDAO: RecipesDAO) {

    suspend fun saveRecipes(recipesEntity: RecipesEntity){
        recipesDAO.saveRecipes(recipesEntity)
    }

    fun getRecipes(): Flow<List<RecipesEntity>> {
        return recipesDAO.getRecipes()
    }

    fun getFavouriteRecipes():Flow<List<FavouriteRecipeEntity>>{
        return recipesDAO.getFavouriteRecipes()
    }

    suspend fun saveFavouriteRecipe(recipe:FavouriteRecipeEntity){
        recipesDAO.saveFavouriteRecipe(recipe)
    }

    suspend fun deleteFavouriteRecipe(recipe: FavouriteRecipeEntity){
        recipesDAO.deleteFavouriteRecipe(recipe)
    }

    suspend fun deleteAllFavouriteRecipes(){
        recipesDAO.deleteAllFavouriteRecipes()
    }




}