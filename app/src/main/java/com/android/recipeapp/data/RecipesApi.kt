package com.android.recipeapp.data

import com.android.recipeapp.models.FoodRecipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RecipesApi  {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries:Map<String,String>):Response<FoodRecipes>
}