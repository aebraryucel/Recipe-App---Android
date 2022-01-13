package com.android.recipeapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.recipeapp.R
import com.android.recipeapp.databinding.FragmentRecipeOverviewBinding
import com.android.recipeapp.models.Result

class RecipeOverviewFragment : Fragment() {

    private lateinit var binding: FragmentRecipeOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_recipe_overview,container,false)

        val recipe:Result? = arguments?.getParcelable("recipe")
        recipe?.let {
            binding.resultRecipe=recipe
        }

        return binding.root
    }


}