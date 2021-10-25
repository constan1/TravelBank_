package com.exercise.travelbank_.models

import android.os.Parcelable

import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Attachments(
    val thumbnails : @RawValue List<Thumbnails>
):Parcelable