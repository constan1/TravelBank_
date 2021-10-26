package com.exercise.travelbank_.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Thumbnails(
    @SerializedName("list")
    val list: String
):Parcelable