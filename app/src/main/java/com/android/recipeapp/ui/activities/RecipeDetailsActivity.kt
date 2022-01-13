package com.android.recipeapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import com.android.recipeapp.databinding.ActivityRecipeDetailsBinding
import com.android.recipeapp.ui.adapters.RecipeDetailsViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.android.recipeapp.R
import com.android.recipeapp.data.database.FavouriteRecipeEntity
import com.android.recipeapp.models.Result
import com.android.recipeapp.ui.fragments.HomeFragmentDirections
import com.android.recipeapp.ui.fragments.RecipeIngredientsFragment
import com.android.recipeapp.ui.fragments.RecipeInstructionsFragment
import com.android.recipeapp.ui.fragments.RecipeOverviewFragment
import com.android.recipeapp.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator


@AndroidEntryPoint
class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRecipeDetailsBinding
    private val args by navArgs<RecipeDetailsActivityArgs>()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details)

        val recipe: Result? = args.toBundle().getParcelable("recipe")

        recipe?.let {
            binding.apply {
                recipeResult=args.recipe
                activity=this@RecipeDetailsActivity
            }
        }

        initTabLayoutWithViewPager(initFragments())
    }

    private fun initFragments():List<Fragment>{
        val overviewFragment = RecipeOverviewFragment()
        overviewFragment.arguments=createBundle()

        val ingredientsFragment = RecipeIngredientsFragment()
        ingredientsFragment.arguments=createBundle()

        val instructionsFragment = RecipeInstructionsFragment()
        instructionsFragment.arguments=createBundle()

        return listOf(overviewFragment,ingredientsFragment,instructionsFragment)
    }



    fun initTabLayoutWithViewPager(fragments:List<Fragment>){
        val pagerAdapter=RecipeDetailsViewPagerAdapter(fragments,supportFragmentManager,lifecycle)
        binding.apply {
            pager.adapter=pagerAdapter
            TabLayoutMediator(tabLayout,pager) { tab, position ->
                when(position){
                    0->{tab.text="Overview"}
                    1->{tab.text="Ingredients"}
                    2->{tab.text="Instructions"}
                }

            }.attach()
        }
    }


    private fun createBundle():Bundle{
        val bundle = Bundle()
        bundle.putParcelable("recipe",args.recipe)
        return bundle
    }


    fun deleteRecipeFromFavourites(recipe: Result){
        viewModel.deleteFavouriteRecipeFromDb(FavouriteRecipeEntity(recipe.id,recipe))
    }


    fun deleteAllRecipesFromFavourites(){
        viewModel.deleteAllFavouriteRecipesFromDb()
    }

    fun saveFavouriteRecipe(recipe: Result){
        viewModel.saveFavouriteRecipeToDb(FavouriteRecipeEntity(recipe.id,recipe))
    }



}