package com.android.recipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.recipeapp.R
import com.android.recipeapp.databinding.IngredientsRowLayoutBinding
import com.android.recipeapp.models.ExtendedIngredient

class RecipeIngredientsFragmentAdapter(val ingredients:List<ExtendedIngredient>): RecyclerView.Adapter<RecipeIngredientsFragmentAdapter.AdapterViewHolder>() {
    class AdapterViewHolder(val binding: IngredientsRowLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: ExtendedIngredient) {
            binding.apply {
                binding.ingredient = ingredient
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<IngredientsRowLayoutBinding>(inflater, R.layout.ingredients_row_layout,parent,false)
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }


}
