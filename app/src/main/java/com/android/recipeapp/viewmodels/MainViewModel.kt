package com.android.recipeapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.android.recipeapp.util.Resource
import com.android.recipeapp.data.Repository
import com.android.recipeapp.data.database.FavouriteRecipeEntity
import com.android.recipeapp.data.database.RecipesEntity
import com.android.recipeapp.models.FoodRecipes
import com.android.recipeapp.ui.fragments.SearchOptionsFragment
import com.android.recipeapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application, private val repository: Repository):AndroidViewModel(application) {

    //RETROFIT
    var recipes:MutableLiveData<Resource<FoodRecipes>> = MutableLiveData()


    //ROOM
    var databaseRecipes : LiveData<List<RecipesEntity>> = repository.localSource.getRecipes().asLiveData()
    var favouriteRecipes: LiveData<List<FavouriteRecipeEntity>> = repository.localSource.getFavouriteRecipes().asLiveData()



    fun saveRecipesToDb(recipes: RecipesEntity)=viewModelScope.launch(Dispatchers.IO){
        repository.localSource.saveRecipes(recipes)
    }

    fun saveFavouriteRecipeToDb(recipe:FavouriteRecipeEntity)=viewModelScope.launch(Dispatchers.IO){
        repository.localSource.saveFavouriteRecipe(recipe)
    }

    fun deleteFavouriteRecipeFromDb(recipe: FavouriteRecipeEntity)=viewModelScope.launch(Dispatchers.IO){
        repository.localSource.deleteFavouriteRecipe(recipe)
    }

    fun deleteAllFavouriteRecipesFromDb()=viewModelScope.launch(Dispatchers.IO){
        repository.localSource.deleteAllFavouriteRecipes()
    }



    fun getRecipes(queries:Map<String,String>)=viewModelScope.launch{
        if(hasInternetConnection()){
            try {
                val response = repository.remoteSource.getRecipes(queries)
                recipes.value=handleResponse(response)

                val recipe = recipes.value!!.data
                recipe?.let {
                    val recipesEntity = RecipesEntity(recipe)
                    saveRecipesToDb(recipesEntity)
                }



            }catch (e:Exception){
                recipes.postValue(Resource.Error(e.toString()))
            }

        }else{
            recipes.postValue(Resource.Error("Connection Error!",null))
        }

    }

    private fun handleResponse(response: Response<FoodRecipes>): Resource<FoodRecipes> {
        when{
            response.code() == 402 ->{
                return Resource.Error("API Key Limitation! , API Key is not able send more request today.")
            }

            response.body()!!.results.isNullOrEmpty()->{
                return Resource.Error("Recipes not found")
            }
            response.isSuccessful->{
                return Resource.Success(response.body())
            }
            else->{
                return Resource.Error(response.message() + " Error Code : "+ response.code())
            }

        }


    }


    private fun hasInternetConnection():Boolean{
        val connectivityManager=getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork=connectivityManager.activeNetwork?: return false
        val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork) ?:return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)-> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)-> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)-> true
            else->false
        }
    }

     fun getQueries(keyword:String?= null):HashMap<String,String>{
        val queries:HashMap<String,String> = HashMap()
        queries[Constants.QUERY_PARAMETER_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_PARAMETER_RECIPE_INFO] = Constants.QUERY_TRUE
        queries[Constants.QUERY_PARAMETER_FILL_INGREDIENTS] = Constants.QUERY_TRUE
        queries[Constants.QUERY_PARAMETER_RESULT_NUMBER]= Constants.QUERY_RESULT_NUMBER

        keyword?.let {
            queries[Constants.QUERY_PARAMETER_NAME]=keyword
        }

        queries[Constants.QUERY_PARAMETER_MIN_CALORIES] = SearchOptionsFragment.currentRangeSliderMinValue.toInt().toString()
        queries[Constants.QUERY_PARAMETER_MAX_CALORIES] = SearchOptionsFragment.currentRangeSliderMaxValue.toInt().toString()
        queries[Constants.QUERY_PARAMETER_MAX_TIME] = SearchOptionsFragment.currentSliderMaxValue.toInt().toString()
        queries[Constants.QUERY_PARAMETER_SORT_TYPE] = SearchOptionsFragment.currentSelectedMenuItem

        if(SearchOptionsFragment.currentCheckedDietTypeChipId!=-1){
            queries[Constants.QUERY_PARAMETER_DIET_TYPE]= SearchOptionsFragment.currentCheckedDietTypeChipName
        }

        if(SearchOptionsFragment.currentCheckedMealTypeChipId!=-1){
            queries[Constants.QUERY_PARAMETER_MEAL_TYPE]= SearchOptionsFragment.currentCheckedMealTypeChipName
        }

        return queries
    }



}