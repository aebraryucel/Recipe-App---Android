package com.android.recipeapp.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.android.recipeapp.R
import com.android.recipeapp.models.FoodRecipes
import com.android.recipeapp.models.UserPreferences
import com.android.recipeapp.util.Constants
import com.android.recipeapp.util.Constants.Companion.CALORIES_MAX_KEY
import com.android.recipeapp.util.Constants.Companion.CALORIES_MIN_KEY
import com.android.recipeapp.util.Constants.Companion.DATASTORE_NAME
import com.android.recipeapp.util.Constants.Companion.DEFAULT_SELECTED_SORT_INDEX
import com.android.recipeapp.util.Constants.Companion.DEFAULT_SELECTED_SORT_MENU_POSITION
import com.android.recipeapp.util.Constants.Companion.DIET_TYPE_DEFAULT_SELECTED_CHIP_ID
import com.android.recipeapp.util.Constants.Companion.DIET_TYPE_DEFAULT_SELECTED_CHIP_NAME
import com.android.recipeapp.util.Constants.Companion.FAVOURITES_SORT_TYPE_KEY
import com.android.recipeapp.util.Constants.Companion.MEAL_TYPE_DEFAULT_SELECTED_CHIP_ID
import com.android.recipeapp.util.Constants.Companion.MEAL_TYPE_DEFAULT_SELECTED_CHIP_NAME
import com.android.recipeapp.util.Constants.Companion.RANGE_SLIDER_DEFAULT_MAX_VALUE
import com.android.recipeapp.util.Constants.Companion.RANGE_SLIDER_DEFAULT_MIN_VALUE
import com.android.recipeapp.util.Constants.Companion.SELECTED_DIET_CHIP_ID_KEY
import com.android.recipeapp.util.Constants.Companion.SELECTED_DIET_CHIP_NAME_KEY
import com.android.recipeapp.util.Constants.Companion.SELECTED_MEAL_TYPE_CHIP_ID_KEY
import com.android.recipeapp.util.Constants.Companion.SELECTED_MEAL_TYPE_CHIP_NAME_KEY
import com.android.recipeapp.util.Constants.Companion.SLIDER_DEFAULT_MAX_VALUE
import com.android.recipeapp.util.Constants.Companion.SORT_MENU_STATUS_KEY
import com.android.recipeapp.util.Constants.Companion.TIME_MAX_KEY
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import kotlin.math.min


@ActivityRetainedScoped
class PreferencesDataStore @Inject constructor(@ApplicationContext private val context: Context){

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    //Search Options for Home Fragment

    suspend fun saveUserPreferences(userPreferences: UserPreferences){
        context.dataStore.edit {preferences->
            preferences[stringPreferencesKey(CALORIES_MIN_KEY)]=userPreferences.calorieSliderMinValue
            preferences[stringPreferencesKey(CALORIES_MAX_KEY)]=userPreferences.calorieSliderMaxValue
            preferences[stringPreferencesKey(TIME_MAX_KEY)]=userPreferences.timeSliderMaxValue
            preferences[intPreferencesKey(SELECTED_DIET_CHIP_ID_KEY)]=userPreferences.dietTypeChipId
            preferences[stringPreferencesKey(SELECTED_DIET_CHIP_NAME_KEY)]=userPreferences.dietTypeChipName
            preferences[intPreferencesKey(SELECTED_MEAL_TYPE_CHIP_ID_KEY)]=userPreferences.mealTypeChipId
            preferences[stringPreferencesKey(SELECTED_MEAL_TYPE_CHIP_NAME_KEY)]=userPreferences.mealTypeChipName
            preferences[stringPreferencesKey(SORT_MENU_STATUS_KEY)]=userPreferences.selectedSort
        }
    }

    val userPreferences:Flow<UserPreferences> = context.dataStore.data.map {
        UserPreferences(
            it[stringPreferencesKey(CALORIES_MIN_KEY)]?:RANGE_SLIDER_DEFAULT_MIN_VALUE.toString(),
            it[stringPreferencesKey(CALORIES_MAX_KEY)]?:RANGE_SLIDER_DEFAULT_MAX_VALUE.toString(),
            it[stringPreferencesKey(TIME_MAX_KEY)]?: SLIDER_DEFAULT_MAX_VALUE.toString(),
            it[intPreferencesKey(SELECTED_DIET_CHIP_ID_KEY)]?: DIET_TYPE_DEFAULT_SELECTED_CHIP_ID,
            it[stringPreferencesKey(SELECTED_DIET_CHIP_NAME_KEY)]?: DIET_TYPE_DEFAULT_SELECTED_CHIP_NAME,
            it[intPreferencesKey(SELECTED_MEAL_TYPE_CHIP_ID_KEY)]?: MEAL_TYPE_DEFAULT_SELECTED_CHIP_ID,
            it[stringPreferencesKey(SELECTED_MEAL_TYPE_CHIP_NAME_KEY)]?: MEAL_TYPE_DEFAULT_SELECTED_CHIP_NAME,
            it[stringPreferencesKey(SORT_MENU_STATUS_KEY)]?: DEFAULT_SELECTED_SORT_MENU_POSITION.toString()
        )
    }


    //Sort options for Favourites Fragment

    suspend fun saveFavouritesSortIndex(selectedSortIndex:Int,key:String){
        context.dataStore.edit {preferences->
            preferences[intPreferencesKey(key)]=selectedSortIndex
        }
    }

    val favouritesSelectedSortIndex:Flow<Int> = context.dataStore.data.map {
        it[intPreferencesKey(FAVOURITES_SORT_TYPE_KEY)]?: DEFAULT_SELECTED_SORT_INDEX
    }








    }
