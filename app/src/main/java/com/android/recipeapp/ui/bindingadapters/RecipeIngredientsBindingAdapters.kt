package com.android.recipeapp.ui.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.recipeapp.models.Result


class RecipeIngredientsBindingAdapters {
    companion object {
        @BindingAdapter("setCalories")
        @JvmStatic
        fun setCalories(caloriesText: TextView, recipe:Result) {
            val calorie=recipe.nutrition.nutrients[0].amount
            val text=calorie.toInt().toString()+" "+recipe.nutrition.nutrients[0].unit
            caloriesText.setText(text)
        }

    }
}
