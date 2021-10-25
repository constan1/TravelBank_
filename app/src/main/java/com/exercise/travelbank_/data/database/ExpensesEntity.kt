package com.exercise.travelbank_.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.exercise.travelbank_.data.ExpensesResponse
import com.exercise.travelbank_.models.Attachments
import kotlinx.android.parcel.RawValue


@Entity(tableName = "Expenses")
class ExpensesEntity(

    var expensesResponse: ExpensesResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}