package com.android.recipeapp.util

import com.android.recipeapp.R


class Constants {

    companion object{

        //RETROFIT
        const val API_KEY="f9655e9ef6514ebb9c8722677812f355"
        const val BASE_URL="https://api.spoonacular.com"

        const val QUERY_TRUE="true"
        const val QUERY_RESULT_NUMBER="20"

        const val QUERY_PARAMETER_API_KEY="apiKey"
        const val QUERY_PARAMETER_RECIPE_INFO="addRecipeInformation"
        const val QUERY_PARAMETER_FILL_INGREDIENTS="fillIngredients"
        const val QUERY_PARAMETER_RESULT_NUMBER="number"
        const val QUERY_PARAMETER_NAME="query"
        const val QUERY_PARAMETER_MIN_CALORIES="minCalories"
        const val QUERY_PARAMETER_MAX_CALORIES="maxCalories"
        const val QUERY_PARAMETER_MAX_TIME="maxReadyTime"
        const val QUERY_PARAMETER_DIET_TYPE="diet"
        const val QUERY_PARAMETER_MEAL_TYPE="type"
        const val QUERY_PARAMETER_SORT_TYPE="sort"



        // Home Fragment Search Options Preferences

        const val DATASTORE_NAME="Preferences_DataStore"

        const val RANGE_SLIDER_DEFAULT_MIN_VALUE=100
        const val RANGE_SLIDER_DEFAULT_MAX_VALUE=1000
        const val SLIDER_DEFAULT_MAX_VALUE=240
        const val MEAL_TYPE_DEFAULT_SELECTED_CHIP_ID= -1
        const val MEAL_TYPE_DEFAULT_SELECTED_CHIP_NAME=""
        const val DIET_TYPE_DEFAULT_SELECTED_CHIP_NAME=""
        const val DIET_TYPE_DEFAULT_SELECTED_CHIP_ID= -1
        const val DEFAULT_SELECTED_SORT_MENU_POSITION = 0
        const val DEFAULT_SELECTED_SORT_MENU_ITEM="healthiness"

        const val TIME_MAX_KEY="TimeMax"
        const val CALORIES_MIN_KEY="CaloriesMin"
        const val CALORIES_MAX_KEY="CaloriesMax"
        const val SELECTED_DIET_CHIP_NAME_KEY="dietTypeChipName"
        const val SELECTED_DIET_CHIP_ID_KEY="dietTypeChipId"
        const val SELECTED_MEAL_TYPE_CHIP_NAME_KEY="mealTypeChipName"
        const val SELECTED_MEAL_TYPE_CHIP_ID_KEY="mealTypeChipId"
        const val SORT_MENU_STATUS_KEY="SortMenuStatus"


        //Favourites Fragment Sort Preferences

        const val FAVOURITES_SORT_TYPE_HEALTHINESS="Healthiness"
        const val FAVOURITES_SORT_TYPE_TIME="Cooking Time"
        const val FAVOURITES_SORT_TYPE_POPULARITY="Popularity"
        const val FAVOURITES_SORT_TYPE_CALORIES="Calories"

        const val FAVOURITES_SORT_TYPE_KEY="favouritesSortType"

        const val DEFAULT_SELECTED_SORT_INDEX = 0


        //ROOM DATABASE

        const val DATABASE_NAME="recipe_db"
        const val TABLE_RECIPES="table_recipes"
        const val TABLE_FAVOURITE_RECIPES="table_favourite_recipes"

    }

}