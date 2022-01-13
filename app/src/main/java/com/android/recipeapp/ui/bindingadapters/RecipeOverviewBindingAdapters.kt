package com.android.recipeapp.ui.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.recipeapp.R
import org.jsoup.Jsoup

class RecipeOverviewBindingAdapters {

    companion object {

        @BindingAdapter("setDietType")
        @JvmStatic
        fun setDietType(imageView: ImageView, type: Boolean) {
            if(type){
                imageView.setImageResource(R.drawable.ic_tickicon)
            }else{
                imageView.setImageResource(R.drawable.ic_crossicon)
            }

        }


        @BindingAdapter("setSummary")
        @JvmStatic
        fun setSummary(textView: TextView, text: String?) {
            text?.let {
                textView.text = Jsoup.parse(text).text()
            }
        }





    }
}