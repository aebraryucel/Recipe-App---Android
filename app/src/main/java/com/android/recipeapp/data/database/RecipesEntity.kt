package com.android.recipeapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.recipeapp.models.FoodRecipes
import com.android.recipeapp.util.Constants.Companion.TABLE_RECIPES

@Entity(tableName = TABLE_RECIPES)
class RecipesEntity(val recipes: FoodRecipes) {
    @PrimaryKey(autoGenerate = false)
    var id:Int=0

}