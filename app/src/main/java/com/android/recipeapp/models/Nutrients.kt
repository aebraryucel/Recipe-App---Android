package com.android.recipeapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nutrients(
    @SerializedName("name")
    val name : String,
    @SerializedName("amount")
    val amount : Double,
    @SerializedName("unit")
    val unit : String
):Parcelable
