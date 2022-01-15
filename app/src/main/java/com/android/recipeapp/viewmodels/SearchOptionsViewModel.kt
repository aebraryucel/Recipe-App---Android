package com.android.recipeapp.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recipeapp.R
import com.android.recipeapp.data.PreferencesDataStore
import com.android.recipeapp.models.UserPreferences
import com.android.recipeapp.util.Constants
import com.android.recipeapp.util.Constants.Companion.API_KEY
import com.android.recipeapp.util.Constants.Companion.QUERY_PARAMETER_API_KEY
import com.android.recipeapp.util.Constants.Companion.QUERY_PARAMETER_DIET_TYPE
import com.android.recipeapp.util.Constants.Companion.QUERY_PARAMETER_FILL_INGREDIENTS
import com.android.recipeapp.util.Constants.Companion.QUERY_PARAMETER_MAX_CALORIES
import com.android.recipeapp.util.Constants.Companion.QUERY_PARAMETER_MAX_TIME
import com.android.recipeapp.util.Constants.Companion.QUERY_PARAMETER_MEAL_TYPE
import com.android.recipeapp.util.Constants.Companion.QUERY_PARAMETER_MIN_CALORIES
import com.android.recipeapp.util.Constants.Companion.QUERY_PARAMETER_RECIPE_INFO
import com.android.recipeapp.util.Constants.Companion.QUERY_PARAMETER_RESULT_NUMBER
import com.android.recipeapp.util.Constants.Companion.QUERY_PARAMETER_SORT_TYPE
import com.android.recipeapp.util.Constants.Companion.QUERY_RESULT_NUMBER
import com.android.recipeapp.util.Constants.Companion.QUERY_TRUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@HiltViewModel
class SearchOptionsViewModel @Inject constructor(application: Application,private val dataStore: PreferencesDataStore): AndroidViewModel(application){

    val userPreferences:Flow<UserPreferences> = dataStore.userPreferences

    fun saveUserPreferences(userPreferences: UserPreferences)=viewModelScope.launch(Dispatchers.IO){
        dataStore.saveUserPreferences(userPreferences)
    }

    fun getQueries(keyword:String?= null):HashMap<String,String>{
        val queries:HashMap<String,String> = HashMap()
        queries[QUERY_PARAMETER_API_KEY] = API_KEY
        queries[QUERY_PARAMETER_RECIPE_INFO] = QUERY_TRUE
        queries[QUERY_PARAMETER_FILL_INGREDIENTS] = QUERY_TRUE
        queries[QUERY_PARAMETER_RESULT_NUMBER]= QUERY_RESULT_NUMBER


        keyword?.let {
            queries[Constants.QUERY_PARAMETER_NAME]=keyword
        }


        viewModelScope.launch {
            userPreferences.collect {
                queries[QUERY_PARAMETER_MIN_CALORIES]=it.calorieSliderMinValue.toFloat().toInt().toString()
                queries[QUERY_PARAMETER_MAX_CALORIES]=it.calorieSliderMaxValue.toFloat().toInt().toString()
                queries[QUERY_PARAMETER_MAX_TIME]=it.timeSliderMaxValue.toFloat().toInt().toString()

                val selectedItem = getApplication<Application>().resources.getStringArray(R.array.sortOptions)[it.selectedSort.toInt()]
                queries[QUERY_PARAMETER_SORT_TYPE]=selectedItem.lowercase()


                if(it.dietTypeChipId!=-1){
                    queries[QUERY_PARAMETER_DIET_TYPE]=it.dietTypeChipName
                }
                if(it.mealTypeChipId!=-1){
                    queries[QUERY_PARAMETER_MEAL_TYPE]=it.mealTypeChipName
                }
            }
        }

        return queries
    }



}