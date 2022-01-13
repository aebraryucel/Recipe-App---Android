package com.android.recipeapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Nutrition(
    @SerializedName("nutrients")
    val nutrients : @RawValue List<Nutrients>
):Parcelable
