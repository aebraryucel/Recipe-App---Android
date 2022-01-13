package com.android.recipeapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.recipeapp.models.Result
import com.android.recipeapp.util.Constants.Companion.TABLE_FAVOURITE_RECIPES

@Entity(tableName = TABLE_FAVOURITE_RECIPES)
class FavouriteRecipeEntity(
    @PrimaryKey
    var id: Int,
    var recipe:Result
    )