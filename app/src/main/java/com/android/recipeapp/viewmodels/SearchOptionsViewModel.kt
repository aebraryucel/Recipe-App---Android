package com.android.recipeapp.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recipeapp.data.PreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class SearchOptionsViewModel @Inject constructor(private val dataStore: PreferencesDataStore): ViewModel(){

    val calorieSliderMinValue: Flow<String> = dataStore.rangeSliderMinValueFlow
    val calorieSliderMaxValue: Flow<String> = dataStore.rangeSliderMaxValueFlow

    val timeSliderMaxValue:Flow<String> = dataStore.sliderMaxValueFlow

    val dietTypeChipId: Flow<String> = dataStore.dietTypeChipId
    val mealTypeChipId:Flow<String> = dataStore.mealTypeChipId

    val selectedSort:Flow<String> = dataStore.sortType



    fun saveRangeSliderValue(startValue: String,endValue:String, minKey:String, maxKey:String)=viewModelScope.launch(Dispatchers.IO){
        dataStore.saveRangeSliderPreferenceValue(startValue,endValue,minKey, maxKey)
    }

    fun saveSliderValue(value: String, key: String)=viewModelScope.launch(Dispatchers.IO) {
        dataStore.saveSliderPreferenceValue(value,key)
    }

    fun saveSelectedChipId(selectedChip: Int,key:String)=viewModelScope.launch(Dispatchers.IO){
        dataStore.saveSelectedChipIds(selectedChip,key)
    }

    fun saveSortType(selectedMenuItemPosition: Int,key:String)=viewModelScope.launch(Dispatchers.IO) {
        dataStore.saveSortType(selectedMenuItemPosition,key)
    }




}