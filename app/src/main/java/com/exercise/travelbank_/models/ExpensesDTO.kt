package com.exercise.travelbank_.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue



@Parcelize
data class ExpensesDTO(
    val amount: Double?,
    val attachments: @RawValue Attachments?,
    val date:String,
    val description: String,
    val expenseVenueTitle: String?,
    val tripBudgetCategory:String,
    val currencyCode:String?,




):Parcelable