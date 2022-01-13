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
import com.android.recipeapp.databinding.FragmentRecipeIngredientsBinding
import com.android.recipeapp.databinding.FragmentRecipeInstructionsBinding
import com.android.recipeapp.models.ExtendedIngredient
import com.android.recipeapp.models.Result
import com.android.recipeapp.models.Step
import com.android.recipeapp.ui.adapters.RecipeIngredientsFragmentAdapter
import com.android.recipeapp.ui.adapters.RecipeInstructionsFragmentAdapter


class RecipeIngredientsFragment : Fragment() {

    private lateinit var binding: FragmentRecipeIngredientsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_recipe_ingredients,container,false)
        val recipe: Result? = arguments?.getParcelable("recipe")

        recipe?.let {
            binding.recipeResult=recipe
            initRecyclerView(recipe.extendedIngredients)
        } ?: run {
            initRecyclerView(emptyList())
            Log.d("RECIPE INGREDIENTS FRAGMENT","ARGUMENTS NOT RECEIVED")
        }

        return binding.root
    }

    fun initRecyclerView(list: List<ExtendedIngredient>) {
        binding.ingredientsRecyclerView.apply {
            adapter = RecipeIngredientsFragmentAdapter(list)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
    }

}