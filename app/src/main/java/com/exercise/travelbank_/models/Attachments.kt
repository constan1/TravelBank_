package com.exercise.travelbank_.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

import kotlinx.parcelize.Parcelize


@Parcelize
data class Attachments(
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails
) : Parcelable