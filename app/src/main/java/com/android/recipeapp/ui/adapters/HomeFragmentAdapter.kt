package com.android.recipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.recipeapp.R
import com.android.recipeapp.databinding.RecipeCardLayoutBinding
import com.android.recipeapp.models.Result
import com.android.recipeapp.ui.fragments.HomeFragment
import com.android.recipeapp.ui.fragments.HomeFragmentDirections

class HomeFragmentAdapter(var recipes: List<Result>):RecyclerView.Adapter<HomeFragmentAdapter.AdapterViewHolder>(){

    class AdapterViewHolder(val binding: RecipeCardLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Result){
            binding.apply {
                result=recipe
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=DataBindingUtil.inflate<RecipeCardLayoutBinding>(inflater, R.layout.recipe_card_layout,parent,false)
        return AdapterViewHolder(binding)

    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int {
       return recipes.size
    }



}