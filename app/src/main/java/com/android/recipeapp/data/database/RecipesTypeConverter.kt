package com.android.recipeapp.data.database

import androidx.room.TypeConverter
import com.android.recipeapp.models.FoodRecipes
import com.android.recipeapp.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    @TypeConverter
    fun recipeToJSON(foodRecipes: FoodRecipes):String{
        return Gson().toJson(foodRecipes)
    }


    @TypeConverter
    fun jsonToRecipe(json:String):FoodRecipes{
        val type = object :TypeToken<FoodRecipes>(){}.type
        return Gson().fromJson(json,type)
    }

    @TypeConverter
    fun singleRecipeToJSON(result: Result):String{
        return Gson().toJson(result)
    }

    @TypeConverter
    fun jsonToSingleRecipe(json:String):Result{
        val type = object :TypeToken<Result>(){}.type
        return Gson().fromJson(json,type)
    }



}