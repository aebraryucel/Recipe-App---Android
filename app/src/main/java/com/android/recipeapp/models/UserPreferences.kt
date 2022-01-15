package com.android.recipeapp.models

data class UserPreferences(
    val calorieSliderMinValue:String,
    val calorieSliderMaxValue:String,
    val timeSliderMaxValue:String,
    val dietTypeChipId:Int,
    val dietTypeChipName:String,
    val mealTypeChipId:Int,
    val mealTypeChipName:String,
    val selectedSort:String
)
