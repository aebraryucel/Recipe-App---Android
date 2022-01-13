package com.android.recipeapp.ui.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.recipeapp.R
import com.android.recipeapp.models.Result
import com.android.recipeapp.ui.activities.RecipeDetailsActivity

class RecipeDetailsActivityBindingAdapters {

    companion object {

        @BindingAdapter("setFavouriteIcon")
        @JvmStatic
        fun setFavouriteIcon(view: ImageView, isFavourite: Boolean) {
            if (isFavourite) {
                view.setImageResource(R.drawable.ic_liked)
            } else {
                view.setImageResource(R.drawable.ic_like)
            }
        }


        @BindingAdapter("favouriteIconOnClickArg1", "favouriteIconOnClickArg2")
        @JvmStatic
        fun favouriteIconOnClick(view: ImageView, result: Result, activity: RecipeDetailsActivity) {
            view.setOnClickListener {
                if (result.isFavorite) {
                    result.isFavorite=false
                    activity.deleteRecipeFromFavourites(result)
                    view.setImageResource(R.drawable.ic_like)
                } else {
                    result.isFavorite=true
                    activity.saveFavouriteRecipe(result)
                    view.setImageResource(R.drawable.ic_liked)
                }
            }
        }
    }
}