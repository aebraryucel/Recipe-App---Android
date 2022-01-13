package com.android.recipeapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.recipeapp.R
import com.android.recipeapp.databinding.FragmentRecipeInstructionsBinding
import com.android.recipeapp.models.Result
import com.android.recipeapp.models.Step
import com.android.recipeapp.ui.adapters.RecipeInstructionsFragmentAdapter


class RecipeInstructionsFragment : Fragment() {

    private lateinit var binding: FragmentRecipeInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_recipe_instructions,container,false)
        val recipe: Result? = arguments?.getParcelable("recipe")

        recipe?.let {
            if(!recipe.analyzedInstructions.isNullOrEmpty()){
                initRecyclerView(recipe.analyzedInstructions[0].steps)
            }
        } ?: run {
            initRecyclerView(emptyList())
        }
        return binding.root
    }


    fun initRecyclerView(list:List<Step>){
        binding.instructionsRecyclerView.apply {
            adapter= RecipeInstructionsFragmentAdapter(list)
            layoutManager= LinearLayoutManager(requireContext())
        }

    }

}