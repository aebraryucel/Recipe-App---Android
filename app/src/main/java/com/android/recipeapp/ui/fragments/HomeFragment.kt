package com.android.recipeapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.recipeapp.R
import com.android.recipeapp.data.database.FavouriteRecipeEntity
import com.android.recipeapp.databinding.FragmentHomeBinding
import com.android.recipeapp.models.Result
import com.android.recipeapp.ui.adapters.HomeFragmentAdapter
import com.android.recipeapp.util.Constants
import com.android.recipeapp.util.Resource
import com.android.recipeapp.viewmodels.MainViewModel
import com.android.recipeapp.viewmodels.SearchOptionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(){

    private lateinit var binding:FragmentHomeBinding
    var list = listOf<Result>()

    private val viewModel: MainViewModel by viewModels()
    private val searchOptionsViewModel: SearchOptionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        binding.homeFragment=this
        initRecyclerView()
        searchListener()
        getData()
        return binding.root
    }


    fun getData(){
        if(arguments!=null){
            val isFromSearchOptionsFragment = HomeFragmentArgs.fromBundle(requireArguments()).returnFromOptionsFragment
            if(isFromSearchOptionsFragment){
                Log.d("GET DATA FUNCTION","FROM SEARCH OPTIONS FRAGMENT, GET RECIPES FROM API")
               getRecipesFromApi(searchOptionsViewModel.getQueries())
             }
            else{
                Log.d("GET DATA FUNCTION","GET RECIPES FROM DB, ARGUMENTS NOT NULL")
                getRecipesFromDatabase()
            }
        }
        else{
           getRecipesFromDatabase()
            Log.d("GET DATA FUNCTION","GET RECIPES FROM DB, ARGUMENTS NULL")
        }
    }


    fun initRecyclerView(){
        binding.shimmerRecyclerView.apply {
            adapter=HomeFragmentAdapter(list)
            layoutManager=LinearLayoutManager(requireContext())
            showShimmer()
        }

    }

    fun searchListener(){
        binding.textField.setStartIconOnClickListener {
            binding.textFieldInput.text?.let {
                if(it.length>0){
                    getRecipesFromApi(searchOptionsViewModel.getQueries(keyword = it.toString()))
                }else{
                    getRecipesFromDatabase()
                }
            }

        }
    }


    fun getRecipesFromApi(queries:HashMap<String,String>) = lifecycleScope.launch{
        viewModel.getRecipes(queries)
        Log.d("SEARCH QUERIES",queries.toString())
        viewModel.recipes.observe(viewLifecycleOwner,{response->
            when(response){
                is Resource.Success->{
                    binding.shimmerRecyclerView.hideShimmer()
                    response.data?.let {
                            binding.shimmerRecyclerView.apply {
                                visibility=View.VISIBLE
                                binding.noResultsFound.visibility=View.INVISIBLE
                                adapter = HomeFragmentAdapter(it.results)
                            }
                    }
                }
                is Resource.Error->{
                    binding.shimmerRecyclerView.apply {
                        hideShimmer()
                        visibility=View.INVISIBLE
                    }
                    binding.noResultsFound.apply {
                        visibility=View.VISIBLE
                        text=response.msg
                    }
                }
                is Resource.Loading->{
                    binding.shimmerRecyclerView.showShimmer()
                }
            }

        })
    }


    fun getRecipesFromDatabase() = lifecycleScope.launch{
        viewModel.databaseRecipes.observe(viewLifecycleOwner,{ database->
            if(database.isNotEmpty()){
                binding.shimmerRecyclerView.apply {
                    adapter=HomeFragmentAdapter(database[0].recipes.results)
                    hideShimmer()
                }
            }
            else{
                getRecipesFromApi(searchOptionsViewModel.getQueries())
            }

        })
    }

    fun filterRecipesOnClick(){
        findNavController().navigate(R.id.action_homeFragment_to_searchOptionsFragment)
    }


}