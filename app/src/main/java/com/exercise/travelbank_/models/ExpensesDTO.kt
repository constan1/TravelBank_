package com.exercise.travelbank_.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue



@Parcelize
data class ExpensesDTO(
    @SerializedName("amount")
    val amount: Double,

    @SerializedName("attachments")
    val attachments: @RawValue List<Attachments>,

    @SerializedName("date")
    val date: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("expenseVenueTitle")
    val expenseVenueTitle: String,

    @SerializedName("tripBudgetCategory")
    val tripBudgetCategory: String,

    @SerializedName("currencyCode")
    val currencyCode: String,
):Parcelable