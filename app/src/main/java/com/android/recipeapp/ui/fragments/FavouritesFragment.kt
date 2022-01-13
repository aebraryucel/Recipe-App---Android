package com.android.recipeapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.recipeapp.R
import com.android.recipeapp.data.database.FavouriteRecipeEntity
import com.android.recipeapp.databinding.FragmentFavouritesBinding
import com.android.recipeapp.models.Result
import com.android.recipeapp.ui.adapters.HomeFragmentAdapter
import com.android.recipeapp.util.Constants.Companion.DEFAULT_SELECTED_SORT_INDEX
import com.android.recipeapp.util.Constants.Companion.FAVOURITES_SORT_TYPE_CALORIES
import com.android.recipeapp.util.Constants.Companion.FAVOURITES_SORT_TYPE_HEALTHINESS
import com.android.recipeapp.util.Constants.Companion.FAVOURITES_SORT_TYPE_KEY
import com.android.recipeapp.util.Constants.Companion.FAVOURITES_SORT_TYPE_POPULARITY
import com.android.recipeapp.util.Constants.Companion.FAVOURITES_SORT_TYPE_TIME
import com.android.recipeapp.viewmodels.FavouritesFragmentViewModel
import com.android.recipeapp.viewmodels.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private lateinit var binding:FragmentFavouritesBinding
    private val viewModel: MainViewModel by viewModels()
    private val favouritesViewModel:FavouritesFragmentViewModel by viewModels()

    private var currentSelectedSortIndex:Int = DEFAULT_SELECTED_SORT_INDEX
    private lateinit var currentSelectedSort:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_favourites,container,false)

        init()
        getSortTypeIndex()
        getFavouriteRecipes(sortType = currentSelectedSort)
        searchListener()
        return binding.root
    }


    private fun init(){
        binding.favouritesFragment=this
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        currentSelectedSort=resources.getStringArray(R.array.favouriteRecipesSortOptions)[currentSelectedSortIndex]
    }


    fun getFavouriteRecipes(keyword:String?=null,sortType: String?=null){

        viewModel.favouriteRecipes.observe(viewLifecycleOwner,{recipes->
            if(recipes.isEmpty()){
                binding.apply {
                    recyclerView.visibility=View.INVISIBLE
                    noFavouriteText.visibility=View.VISIBLE
                }

            }else{
                binding.recyclerView.apply {
                    visibility=View.VISIBLE
                    binding.noFavouriteText.visibility=View.INVISIBLE
                    adapter= HomeFragmentAdapter(favouritesViewModel.convertToResultList(searchQuery(keyword,sortType,recipes))) // Using same adapter with home adapter
                }
            }
        })
    }


    fun searchListener(){
        binding.textFieldInput.doOnTextChanged { text, start, before, count ->
            getFavouriteRecipes(text.toString(),currentSelectedSort)
        }
    }


    fun searchQuery(keyword: String?,sortType:String?,recipes:List<FavouriteRecipeEntity>):List<FavouriteRecipeEntity>{
        val filteredRecipes = favouritesViewModel.filterRecipes(keyword,recipes)
        return favouritesViewModel.sortRecipes(filteredRecipes,sortType)

    }

    fun deleteIconOnClick(){
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.apply {
            setTitle(getString(R.string.delete_recipes_alert_title_string))
            setMessage(getString(R.string.delete_recipes_alert_message_string))
            setIcon(android.R.drawable.ic_dialog_alert)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes_string)){ dialogInterface, which ->
                viewModel.deleteAllFavouriteRecipesFromDb()
                dialogInterface.dismiss()
            }
            setNegativeButton(getString(R.string.no_string)){ dialogInterface, which ->
                dialogInterface.cancel()
            }
            create()
            show()
        }

    }

    fun sortIconOnClick(){

        val builder = MaterialAlertDialogBuilder(requireContext())
        val keyword=binding.textFieldInput.text.toString()

        builder.apply {
            setTitle(getString(R.string.sort_recipes_title_string))
            setSingleChoiceItems(R.array.favouriteRecipesSortOptions,currentSelectedSortIndex) { dialog, which ->
                when (which) {
                    0 -> {getFavouriteRecipes(keyword,FAVOURITES_SORT_TYPE_POPULARITY)}
                    1 -> {getFavouriteRecipes(keyword,FAVOURITES_SORT_TYPE_HEALTHINESS)}
                    2 -> {getFavouriteRecipes(keyword,FAVOURITES_SORT_TYPE_TIME)}
                    3 -> {getFavouriteRecipes(keyword,FAVOURITES_SORT_TYPE_CALORIES)}
                    else ->{getFavouriteRecipes(keyword,FAVOURITES_SORT_TYPE_POPULARITY)}
                }
                favouritesViewModel.saveSortType(which,FAVOURITES_SORT_TYPE_KEY)
            }
            setPositiveButton(getString(R.string.sort_recipes_ok_string),{ dialog, which->
                dialog.dismiss()
            })
            show()

        }

    }


    private fun getSortTypeIndex()=lifecycleScope.launch{
        favouritesViewModel.selectedSortIndex.asLiveData().observe(viewLifecycleOwner,{index->
            currentSelectedSortIndex=index
        })
    }


}