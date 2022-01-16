package com.android.recipeapp.ui.bindingadapters

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.recipeapp.R
import com.android.recipeapp.models.Result
import com.android.recipeapp.ui.fragments.FavouritesFragmentDirections
import com.android.recipeapp.ui.fragments.HomeFragment
import com.android.recipeapp.ui.fragments.HomeFragmentDirections
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import android.os.Bundle

class RecipeCardBindingAdapters {
    companion object{

        @BindingAdapter("recipeOnClick")
        @JvmStatic
        fun recipeOnClickListener(layout:ConstraintLayout,recipe:Result){
            layout.setOnClickListener {

                val bundle = Bundle()
                bundle.putParcelable("recipe", recipe)
                Navigation.findNavController(layout).navigate(R.id.recipeDetailsActivity,bundle)

            }
        }


        @BindingAdapter("imgUrl")
        @JvmStatic
        fun setImg(view:ImageView,url:String){
            Glide.with(view.context)
                .setDefaultRequestOptions(glideOptions(view))
                .load(url)
                .into(view)

        }

        private fun glideOptions(view: View): RequestOptions {
            val reqOptions= RequestOptions().apply {
                timeout(5000)
                error(R.drawable.ic_error)
                centerCrop()
                placeholder(circularProgressOptions(view))
            }
            return reqOptions
        }

        private fun circularProgressOptions(view: View): CircularProgressDrawable {
            val circularProgress= CircularProgressDrawable(view.context)
            circularProgress.apply {
                centerRadius=30f
                strokeWidth=5f
                start()
            }
            return circularProgress
        }

    }
}