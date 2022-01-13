package com.android.recipeapp.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExtendedIngredient(
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("original")
    val original: String,
):Parcelable