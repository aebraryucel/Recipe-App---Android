package com.android.recipeapp.data

import com.android.recipeapp.models.FoodRecipes
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


class RemoteDataSource @Inject constructor(private val recipesApi: RecipesApi) {
    suspend fun getRecipes(queries:Map<String,String>):Response<FoodRecipes>{
        return recipesApi.getRecipes(queries)
    }

}