package com.android.recipeapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.recipeapp.data.PreferencesDataStore
import com.android.recipeapp.data.database.FavouriteRecipeEntity
import com.android.recipeapp.models.Result
import com.android.recipeapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesFragmentViewModel @Inject constructor(private val dataStore: PreferencesDataStore): ViewModel(){

    val selectedSortIndex: Flow<Int> = dataStore.favouritesSelectedSortIndex


    fun saveSortType(selectedSortIndex: Int,key:String)=viewModelScope.launch(Dispatchers.IO) {
        dataStore.saveFavouritesSortIndex(selectedSortIndex,key)
    }



    fun filterRecipes(keyword: String?,recipes:List<FavouriteRecipeEntity>):List<FavouriteRecipeEntity>{
        keyword?.let {
            val filteredRecipes = recipes.filter {
                it.recipe.title.lowercase().contains(keyword.lowercase())
            }
            return filteredRecipes
        }
        return recipes
    }



    fun sortRecipes(recipes:List<FavouriteRecipeEntity>, sortType: String?):List<FavouriteRecipeEntity>{
        sortType?.let {
            when(sortType){
                Constants.FAVOURITES_SORT_TYPE_HEALTHINESS ->{
                    return recipes.sortedByDescending{
                        it.recipe.healthScore
                    }
                }
                Constants.FAVOURITES_SORT_TYPE_TIME ->{
                    return recipes.sortedBy {
                        it.recipe.readyInMinutes
                    }
                }

                Constants.FAVOURITES_SORT_TYPE_POPULARITY ->{
                    return recipes.sortedByDescending{
                        it.recipe.aggregateLikes
                    }
                }
                Constants.FAVOURITES_SORT_TYPE_CALORIES ->{
                    return recipes.sortedBy {
                        it.recipe.nutrition.nutrients[0].amount
                    }
                }
                else->return recipes
            }
        }
        return recipes
    }



    fun convertToResultList(list:List<FavouriteRecipeEntity>):List<Result>{
        val resultList = mutableListOf<Result>()

        for(recipe in list){
            resultList.add(recipe.recipe)
        }

        return resultList
    }


}